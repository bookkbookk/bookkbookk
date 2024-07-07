package codesquad.bookkbookk.domain.auth.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.domain.auth.data.dto.BookClubMemberAuthInfo;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorizationJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByBookClubIdAndMemberId(Long bookClubId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND bc.book_club_id = :bookClubId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "WHERE bc.book_club_id = :bookClubId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("bookClubId", bookClubId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(BookClub.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByBookIdAndMemberId(Long bookId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, b.book_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND b.book_id = :bookId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, b.book_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "WHERE b.book_id = :bookId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, b.book_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("bookId", bookId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Book.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByGatheringIdAndMemberId(Long gatheringId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, g.gathering_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN gathering g ON b.book_id = g.book_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND g.gathering_id = :gatheringId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, g.gathering_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN gathering g ON b.book_id = g.book_id " +
                "WHERE g.gathering_id = :gatheringId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, g.gathering_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN gathering g ON b.book_id = g.book_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("gatheringId", gatheringId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Gathering.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByChapterIdAndMemberId(Long chapterId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, c.chapter_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND c.chapter_id = :chapterId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, c.chapter_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "WHERE c.chapter_id = :chapterId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, c.chapter_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("chapterId", chapterId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Chapter.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByTopicIdAndMemberId(Long topicId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, t.topic_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND t.topic_id = :topicId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, t.topic_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "WHERE t.topic_id = :topicId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, t.topic_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("topicId", topicId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Topic.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByBookmarkIdAndMemberId(Long bookmarkId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, bm.bookmark_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND bm.bookmark_id = :bookmarkId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, bm.bookmark_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "WHERE bm.bookmark_id = :bookmarkId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, bm.bookmark_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("bookmarkId", bookmarkId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Bookmark.class));
    }

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByCommentIdAndMemberId(Long commentId, Long memberId) {
        String nativeQuery = "(SELECT bc.book_club_id, m.member_id, cm.comment_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "INNER JOIN comment cm ON bm.bookmark_id = cm.bookmark_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND cm.comment_id = :commentId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, cm.comment_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "INNER JOIN comment cm ON bm.bookmark_id = cm.bookmark_id " +
                "WHERE cm.comment_id = :commentId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT bc.book_club_id, m.member_id, cm.comment_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "INNER JOIN book b ON bc.book_club_id = b.book_club_id " +
                "INNER JOIN chapter c ON b.book_id = c.book_id " +
                "INNER JOIN topic t ON c.chapter_id = t.chapter_id " +
                "INNER JOIN bookmark bm ON t.topic_id = bm.topic_id " +
                "INNER JOIN comment cm ON bm.bookmark_id = cm.bookmark_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("commentId", commentId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, createAuthRowMapper(Comment.class));
    }

    private RowMapper<BookClubMemberAuthInfo> createAuthRowMapper(Class<?> entity) {
        return (resultSet, rowNum) -> {
            Long bookClubId = getLongOrNull(resultSet, "book_club_id");
            Long memberId = getLongOrNull(resultSet, "member_id");
            Long entityId = getEntityIdOrNull(resultSet, entity);

            return new BookClubMemberAuthInfo(bookClubId, memberId, entityId);
        };
    }

    private Long getLongOrNull(ResultSet resultSet, String columnName) throws SQLException {
        Long l = resultSet.getLong(columnName);

        if (resultSet.wasNull()) {
            return null;
        }
        return l;
    }

    private Long getEntityIdOrNull(ResultSet resultSet, Class<?> entity) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        String className = entity.getSimpleName();
        String columnName = convertClassNameToColumnName(className);

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String metaColumnName = metaData.getColumnName(i);

            if (metaColumnName.equals(columnName) || metaColumnName.equals(columnName.toLowerCase())) {
                return getLongOrNull(resultSet, columnName);
            }
        }
        return null;
    }

    private String convertClassNameToColumnName(String className) {
        StringBuilder sb = new StringBuilder();

        sb.append(Character.toUpperCase(className.charAt(0)));
        for (int i = 1; i < className.length(); i++) {
            char c = className.charAt(i);

            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        sb.append("_ID");
        return sb.toString();
    }

}
