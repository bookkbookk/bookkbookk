package codesquad.bookkbookk.domain.chapter.repository;

import static codesquad.bookkbookk.domain.bookmark.data.entity.QBookmark.*;
import static codesquad.bookkbookk.domain.chapter.data.entity.QChapter.*;
import static codesquad.bookkbookk.domain.topic.data.entity.QTopic.*;

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

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.common.error.exception.LastInsertIdDoesNotExistException;
import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChapterCustomRepositoryImpl implements ChapterCustomRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 쿼리 성능 개선하기
    @Override
    public List<Chapter> findAllByBookIdAndStatus(Long bookId, Status status) {
        return jpaQueryFactory
                .selectFrom(chapter)
                .leftJoin(chapter.topics, topic)
                .leftJoin(topic.bookmarks, bookmark)
                .leftJoin(bookmark.writer)
                .where(chapter.book.id.eq(bookId),
                        createChapterStatusCondition(status))
                .distinct()
                .fetch();
    }

    @Override
    public List<Chapter> saveAllInBatch(List<Chapter> chapters) {
        String sql = "INSERT INTO chapter (book_id, status, title) VALUES (:book_id, :status, :title)";
        SqlParameterSource[] batchParameters = createBatchParameters(chapters);

        namedParameterJdbcTemplate.batchUpdate(sql, batchParameters);
        setChapterIds(chapters);
        return chapters;
    }

    private BooleanExpression createChapterStatusCondition(Status status) {
        if (status == Status.ALL) {
            return null;
        }
        return chapter.status.eq(status);
    }


    private SqlParameterSource[] createBatchParameters(List<Chapter> chapters) {
        SqlParameterSource[] batchParameters = new SqlParameterSource[chapters.size()];

        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            MapSqlParameterSource parameters = new MapSqlParameterSource();

            parameters.addValue("book_id", chapter.getBookId(), Types.BIGINT);
            parameters.addValue("status", chapter.getStatus(), Types.VARCHAR);
            parameters.addValue("title", chapter.getTitle(), Types.VARCHAR);
            batchParameters[i] = parameters;
        }
        return batchParameters;
    }

    private void setChapterIds(List<Chapter> chapters) {
        Long lastId = namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(
                "SELECT LAST_INSERT_ID()", (rs, rowNum) -> rs.getLong("LAST_INSERT_ID()")
        );

        if (lastId == null) {
            throw new LastInsertIdDoesNotExistException();
        }

        if (savedInBatch()) {
            setIdsUsingFrontId(chapters, lastId);
        }
        else {
            setIdsInUsingEndId(chapters, lastId);
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

    private void setIdsUsingFrontId(List<Chapter> chapters, Long lastId) {
        for (int i = 0; i < chapters.size(); i++) {
            chapters.get(i).setId(lastId + i);
        }
    }

    private void setIdsInUsingEndId(List<Chapter> chapters, Long lastId) {
        for (int i = 0; i < chapters.size(); i++) {
            chapters.get(i).setId(lastId - (chapters.size() - 1 - i));
        }
    }


}
