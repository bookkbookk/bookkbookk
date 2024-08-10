package codesquad.bookkbookk.domain.topic.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.common.error.exception.DatasourceNotConnectedException;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopicCustomRepositoryImpl implements TopicCustomRepository{

    private static final String TOPIC_INSERT_PREFIX = "INSERT INTO topic (chapter_id, title) VALUES ";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Topic> saveAllInBatch(List<Topic> topics) {
        String topicsBatchInsertQuery = createTopicsBatchInsertQuery(topics);

        jdbcTemplate.update(topicsBatchInsertQuery);
        setChapterIds(topics);
        return topics;
    }

    public String createTopicsBatchInsertQuery(List<Topic> topics) {
        StringBuilder sb = new StringBuilder(TOPIC_INSERT_PREFIX);

        for (int i = 0; i < topics.size(); i++) {
            appendChapterValue(topics.get(i), sb);

            if (i < topics.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private StringBuilder appendChapterValue(Topic topic, StringBuilder sb) {
        return sb.append('(')
                .append(topic.getChapterId()).append(", ")
                .append('\'').append(topic.getTitle()).append('\'').append(')');
    }

    private void setChapterIds(List<Topic> topics) {
        String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
        Long lastId = jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);

        if (isDatabaseH2()) {
            setIdInDesc(topics, lastId);
        } else {
            setIdInAsc(topics, lastId);
        }
    }

    private boolean isDatabaseH2() {
        String url;

        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            url = connection.getMetaData().getURL();
        } catch (SQLException e) {
            throw new DatasourceNotConnectedException();
        }

        String databaseType = extractDatabaseType(url);
        return databaseType.equals("h2");
    }

    private String extractDatabaseType(String url) {
        String[] split = url.split(":");
        return split[1];
    }

    private void setIdInAsc(List<Topic> topics, Long lastId) {
        for (Topic topic : topics) {
            topic.setId(lastId);
            lastId++;
        }
    }

    private void setIdInDesc(List<Topic> topics, Long lastId) {
        for (int i = topics.size() - 1; i >= 0; i--) {
            topics.get(i).setId(lastId);
            lastId--;
        }
    }

}
