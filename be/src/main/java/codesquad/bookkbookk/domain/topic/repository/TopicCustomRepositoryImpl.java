package codesquad.bookkbookk.domain.topic.repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.common.error.exception.LastInsertIdDoesNotExistException;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopicCustomRepositoryImpl implements TopicCustomRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Topic> saveAllInBulk(List<Topic> topics) {
        String sql = "INSERT INTO topic (chapter_id, title) VALUES (:chapter_id, :title)";
        SqlParameterSource[] batchParameters = createBatchParameters(topics);

        namedParameterJdbcTemplate.batchUpdate(sql, batchParameters);
        setTopicIds(topics);
        return topics;
    }

    private SqlParameterSource[] createBatchParameters(List<Topic> topics) {
        SqlParameterSource[] batchParameters = new SqlParameterSource[topics.size()];

        for (int i = 0; i < topics.size(); i++) {
            Topic topic = topics.get(i);
            MapSqlParameterSource parameters = new MapSqlParameterSource();

            parameters.addValue("chapter_id", topic.getChapterId(), Types.BIGINT);
            parameters.addValue("title", topic.getTitle(), Types.VARCHAR);
            batchParameters[i] = parameters;
        }
        return batchParameters;
    }

    private void setTopicIds(List<Topic> topics) {
        Long lastId = namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
                "SELECT LAST_INSERT_ID()", (rs, rowNum) -> rs.getLong("LAST_INSERT_ID()")
        );

        if (lastId == null) {
            throw new LastInsertIdDoesNotExistException();
        }

        if (savedInBatch()) {
            setIdsUsingFrontId(topics, lastId);
        }
        else {
            setIdsInUsingEndId(topics, lastId);
        }

    }

    private boolean savedInBatch() {
        DataSource dataSource = namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
        String datasourceUrl = null;
        try {
            datasourceUrl = dataSource.getConnection().getMetaData().getURL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> properties = extractPropertiesFromDatasource(datasourceUrl);

        return properties.getOrDefault("rewriteBatchedStatements", "").equals("true");
    }

    private Map<String, String> extractPropertiesFromDatasource(String datasourceUrl) {
        Map<String, String> properties = new HashMap<>();

        int paramsIndex = datasourceUrl.indexOf('?');
        if (paramsIndex == -1) {
            return Collections.emptyMap();
        }

        String paramsString = datasourceUrl.substring(paramsIndex + 1);
        String[] params = paramsString.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=", 2);
            if (keyValue.length == 2) {
                properties.put(keyValue[0], keyValue[1]);
            } else {
                properties.put(keyValue[0], "");
            }
        }

        return properties;
    }

    private void setIdsUsingFrontId(List<Topic> topics, Long lastId) {
        for (int i = 0; i < topics.size(); i++) {
            topics.get(i).setId(lastId + i);
        }
    }

    private void setIdsInUsingEndId(List<Topic> topics, Long lastId) {
        for (int i = 0; i < topics.size(); i++) {
            topics.get(i).setId(lastId - (topics.size() - 1 - i));
        }
    }

}
