package com.example.weather.service;

import com.example.weather.dto.WeatherResponseDto;
import com.example.weather.entity.WeatherCache;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.repository.WeatherCacheRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherCacheRepository weatherCacheRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private static final int CACHE_EXPIRY_MINUTES = 10;

    // ✅ Cache size limit
    private static final int MAX_CACHE_ENTRIES = 1000;

    /**
     * Main method to get weather by city.
     * Uses Spring cache AND DB cache together.
     */
    @Override
    @Cacheable(
            value = "weatherCache",
            key = "#city.toLowerCase()",
            unless = "#result == null"
    )
    public WeatherResponseDto getWeather(String city) {

        // 1️⃣ Check DB cache first
        Optional<WeatherCache> cacheOpt = weatherCacheRepository.findByCityIgnoreCase(city);

     // If cache is valid
        if (cacheOpt.isPresent()) {
            WeatherCache cache = cacheOpt.get();

            if (cache.getCachedAt() != null &&
                cache.getCachedAt().isAfter(LocalDateTime.now().minusMinutes(CACHE_EXPIRY_MINUTES))) {

                // ✅ Pass cachedAt to DTO
                return mapToDto(cache.getWeatherJson(), cache.getCachedAt());
            }
        }


        // 2️⃣ Call OpenWeather API
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        String response;

        try {
            response = restTemplate.getForObject(url, String.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new CityNotFoundException(city);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Weather API error: " + e.getStatusCode());

        } catch (Exception e) {
            throw new RuntimeException("Weather service unavailable");
        }

        // 3️⃣ Save / Update DB cache (with MAX_CACHE_ENTRIES check)
        WeatherCache cache = cacheOpt.orElse(new WeatherCache());
        cache.setCity(city.toLowerCase());
        cache.setWeatherJson(response);
        cache.setCachedAt(LocalDateTime.now());

        if (weatherCacheRepository.count() >= MAX_CACHE_ENTRIES) {
            weatherCacheRepository.deleteAll();
        }

        weatherCacheRepository.save(cache);

        // 4️⃣ Convert JSON → DTO
        return mapToDto(response, cache.getCachedAt());

    }

    /**
     * Helper method: Convert JSON string from API / DB cache to WeatherResponseDto
     */
    private WeatherResponseDto mapToDto(String json, LocalDateTime cachedAt) {
        try {
            JsonNode root = objectMapper.readTree(json);

            return new WeatherResponseDto(
                    root.get("name").asText(),
                    root.get("sys").get("country").asText(),
                    root.get("main").get("temp").asDouble(),
                    root.get("main").get("feels_like").asDouble(),
                    root.get("main").get("humidity").asInt(),
                    root.get("weather").get(0).get("main").asText(),
                    root.get("weather").get(0).get("description").asText(),
                    root.get("wind").get("speed").asDouble(),
                    root.get("weather").get(0).get("icon").asText(),  // ✅ icon
                    cachedAt != null ? cachedAt : LocalDateTime.now() // ✅ cachedAt
            );

        } catch (Exception e) {
            throw new RuntimeException("Error parsing weather response");
        }
    }

}
