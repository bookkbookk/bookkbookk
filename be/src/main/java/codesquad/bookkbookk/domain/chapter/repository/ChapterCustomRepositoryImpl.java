package codesquad.bookkbookk.domain.chapter.repository;

import static codesquad.bookkbookk.domain.bookmark.data.entity.QBookmark.*;
import static codesquad.bookkbookk.domain.chapter.data.entity.QChapter.*;
import static codesquad.bookkbookk.domain.topic.data.entity.QTopic.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.common.error.exception.DatasourceNotConnectedException;
import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChapterCustomRepositoryImpl implements ChapterCustomRepository {

    private static final String CHAPTER_INSERT_PREFIX = "INSERT INTO chapter (book_id, status, title) VALUES ";

    private final JdbcTemplate jdbcTemplate;
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
        String chaptersBatchInsertQuery = createChaptersBatchInsertQuery(chapters);

        jdbcTemplate.update(chaptersBatchInsertQuery);
        setChapterIds(chapters);
        return chapters;
    }

    private BooleanExpression createChapterStatusCondition(Status status) {
        if (status == Status.ALL) {
            return null;
        }
        return chapter.status.eq(status);
    }

    public String createChaptersBatchInsertQuery(List<Chapter> chapters) {
        StringBuilder sb = new StringBuilder(CHAPTER_INSERT_PREFIX);

        for (int i = 0; i < chapters.size(); i++) {
            appendChapterValue(chapters.get(i), sb);

            if (i < chapters.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private StringBuilder appendChapterValue(Chapter chapter, StringBuilder sb) {
        return sb.append('(')
                .append(chapter.getBookId()).append(", ")
                .append('\'').append(chapter.getStatus().name()).append('\'').append(", ")
                .append('\'').append(chapter.getTitle()).append('\'').append(')');
    }

    private void setChapterIds(List<Chapter> chapters) {
        String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
        Long lastId = jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);

        if (isDatabaseH2()) {
            setIdInDesc(chapters, lastId);
        } else {
            setIdInAsc(chapters, lastId);
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

    private void setIdInAsc(List<Chapter> chapters, Long lastId) {
        for (Chapter chapter : chapters) {
            chapter.setId(lastId);
            lastId++;
        }
    }

    private void setIdInDesc(List<Chapter> chapters, Long lastId) {
        for (int i = chapters.size() - 1; i >= 0; i--) {
            chapters.get(i).setId(lastId);
            lastId--;
        }
    }

}
