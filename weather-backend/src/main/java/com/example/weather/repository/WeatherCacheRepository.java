package com.example.weather.repository;

import com.example.weather.entity.WeatherCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherCacheRepository extends JpaRepository<WeatherCache, Long> {

    Optional<WeatherCache> findByCityIgnoreCase(String city);
}
