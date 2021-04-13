package ru.example.blog.service;

import org.springframework.http.ResponseEntity;
import ru.example.blog.dto.response.StatisticResponse;
import ru.example.blog.enums.StatisticsType;

public interface StatisticService {
    ResponseEntity<StatisticResponse> getStatistics(StatisticsType type);
}
