package codesquad.bookkbookk.domain.topic.repository;

import java.sql.Types;
import java.util.List;

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
        Long startId = namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
                "SELECT LAST_INSERT_ID()", (rs, rowNum) -> rs.getLong("LAST_INSERT_ID()")
        );

        if (startId == null) {
            throw new LastInsertIdDoesNotExistException();
        }

        for (int i = 0; i < topics.size(); i++) {
            topics.get(i).setId(startId + i);
        }

    }

}
