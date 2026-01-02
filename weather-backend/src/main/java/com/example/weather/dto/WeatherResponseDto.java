package com.example.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDto {

    private String city;
    private String country;

    private double temperature;
    private double feelsLike;
    private int humidity;

    private String weather;
    private String description;
    private double windSpeed;

    private String icon;                // ✅ OpenWeather icon
    private LocalDateTime cachedAt;     // ✅ optional: last cached time
}
