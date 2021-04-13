package ru.example.blog.service.implementations;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.example.blog.dto.response.StatisticResponse;
import ru.example.blog.enums.GlobalSettings;
import ru.example.blog.enums.StatisticsType;
import ru.example.blog.exception.BadRequestException;
import ru.example.blog.model.GlobalSetting;

import ru.example.blog.model.enums.Permission;
import ru.example.blog.repository.GlobalSettingRepository;
import ru.example.blog.service.StatisticService;
import ru.example.blog.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.example.blog.enums.GlobalSettings.Code.STATISTICS_IS_PUBLIC;


@Service
@AllArgsConstructor
public class StatisticServiceDefault implements StatisticService {

    private final GlobalSettingRepository repository;
    private final UserService userService;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override public ResponseEntity<StatisticResponse> getStatistics(StatisticsType type){
        StatisticResponse response = new StatisticResponse();

        GlobalSetting pubStatistic = repository.findByCode(STATISTICS_IS_PUBLIC)
                .orElseThrow(BadRequestException::new);

        if (type.equals(StatisticsType.ALL)) {
            if (pubStatistic.getValue() == GlobalSettings.Value.YES ||
                    userService.getUserPermission(Permission.MODERATE)) {
                response = getStatisticsFromDB(type);
            } else {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }

        if (type.equals(StatisticsType.MY)){
            response = getStatisticsFromDB(type);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private StatisticResponse getStatisticsFromDB(StatisticsType type) {
        StatisticResponse response;

        String userCriteria = "";
        String andUserCriteria = "";
        if (type.equals(StatisticsType.MY)) {
            userCriteria = " WHERE user_id = " + userService.getCurrentUser().getId();
            andUserCriteria = userCriteria.replace("WHERE", "AND");
        }
        String query = "SELECT " +
                "(SELECT SUM(view_count) FROM posts" + userCriteria + ") AS viewsCount, " +
                "(SELECT COUNT(id) FROM posts" + userCriteria + ") AS postsCount, " +
                "(SELECT COUNT(id) FROM post_votes WHERE value < 0 " + andUserCriteria + ") AS dislikesCount, " +
                "(SELECT COUNT(id) FROM post_votes WHERE value > 0"  + andUserCriteria + ") AS likesCount, " +
                "DATE_FORMAT(time,'%Y-%m-%dT%H:%m:%s') AS firstPublication " +
                "FROM posts " + userCriteria +
                " ORDER BY time ASC " +
                "LIMIT 1";

        List<StatisticResponse> item = jdbcTemplate.query(query, new RowMapper<StatisticResponse>(){

            @Override
            public StatisticResponse mapRow(ResultSet rs, int i) throws SQLException {
                StatisticResponse statisticResponse = new StatisticResponse();

                statisticResponse.setLikesCount(rs.getLong("likesCount"));
                statisticResponse.setDislikesCount(rs.getLong("dislikesCount"));
                statisticResponse.setViewsCount(rs.getLong("viewsCount"));
                statisticResponse.setFirstPublication(LocalDateTime.parse(rs.getString("firstPublication")).toEpochSecond(ZoneOffset.UTC));
                statisticResponse.setPostsCount(rs.getLong("postsCount"));
                return statisticResponse;
            }
        });

        response = item.get(0);

        return response;
    }
}
