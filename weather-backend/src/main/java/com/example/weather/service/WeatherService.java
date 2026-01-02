package com.example.weather.service;

import com.example.weather.dto.WeatherResponseDto;

public interface WeatherService {

    WeatherResponseDto getWeather(String city);
}
