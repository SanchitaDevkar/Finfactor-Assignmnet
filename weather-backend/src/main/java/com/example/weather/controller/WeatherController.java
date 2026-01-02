package com.example.weather.controller;

import com.example.weather.dto.WeatherResponseDto;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:52897") 
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * GET endpoint to fetch weather data for a city
     *
     * Example: GET http://localhost:8080/api/weather/Pune
     *
     * @param city name of the city
     * @return weather data as JSON
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponseDto> getWeather(
            @PathVariable String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }


}
