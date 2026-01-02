package com.example.weather.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "weather_cache")
public class WeatherCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String city;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String weatherJson;   // ✅ store raw API response

    private LocalDateTime cachedAt;
}
