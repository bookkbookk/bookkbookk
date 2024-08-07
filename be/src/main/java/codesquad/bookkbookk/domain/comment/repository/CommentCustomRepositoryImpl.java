package codesquad.bookkbookk.domain.comment.repository;

import static codesquad.bookkbookk.domain.comment.data.entity.QComment.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentSliceResponse;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> findAllByBookmarkId(Long bookmarkId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .innerJoin(comment.writer).fetchJoin()
                .where(comment.bookmark.id.eq(bookmarkId))
                .fetch();
    }

    @Override
    public Map<Long, ReadCommentSliceResponse> findSlicesByBookmarkIds(List<Long> bookmarkIds, Integer size) {
        List<Long> commentIds = new ArrayList<>();
        Map<Long, ReadCommentSliceResponse> commentSliceMap = findCommentSliceMap(bookmarkIds, size, commentIds);

        addCommentReactionToCommentSlices(commentSliceMap, commentIds);

        return commentSliceMap;
    }

    private Map<Long, ReadCommentSliceResponse> findCommentSliceMap(List<Long> bookmarkIds, Integer size,
                                                                    List<Long> commentIds) {
        String bookmarkIdsStr = IntStream.range(1, bookmarkIds.size() + 1)
                .mapToObj(i -> ":bookmarkId" + i)
                .collect(Collectors.joining(", "));
        String commentSliceQuery =  "WITH numbered_comment AS " +
                "(SELECT c.comment_id, c.bookmark_id, c.writer_id, c.contents, c.created_time, " +
                "w.nickname AS writer_nickname, w.profile_image_url AS writer_profile_image_url, " +
                "ROW_NUMBER() OVER (PARTITION BY c.bookmark_id ORDER BY c.created_time DESC, c.comment_id DESC) AS row_num " +
                "FROM comment c " +
                "INNER JOIN member w ON c.writer_id = w.member_id " +
                "WHERE c.bookmark_id IN (" + bookmarkIdsStr + ")) " +
                "SELECT * " +
                "FROM numbered_comment nc " +
                "WHERE nc.row_num <= :size " +
                "ORDER BY nc.created_time DESC, nc.comment_id DESC ";

        MapSqlParameterSource commentSliceParameters = createCommentSliceParameters(bookmarkIds, size);

        Map<Long, ReadCommentSliceResponse> commentSliceMap = new HashMap<>();

        jdbcTemplate.query(commentSliceQuery, commentSliceParameters, rs -> {
            Long bookmarkId = rs.getLong("bookmark_id");
            ReadCommentSliceResponse commentSlice = commentSliceMap.computeIfAbsent(bookmarkId, id -> new ReadCommentSliceResponse());

            if (isLastComment(rs, size, commentSlice)) {
                return;
            }
            addCommentToCommentSlice(rs, commentSlice, commentIds);
        });
        return commentSliceMap;
    }

    private  void addCommentReactionToCommentSlices(Map<Long, ReadCommentSliceResponse> commentSliceMap, List<Long> commentIds) {
        String commentIdsStr = IntStream.range(1, commentIds.size() + 1)
                .mapToObj(i -> ":commentId" + i)
                .collect(Collectors.joining(", "));
        String findCommentReactionsNativeQuery = "SELECT cr.reaction, cr.comment_id, r.nickname, c.bookmark_id " +
                "FROM comment_reaction cr " +
                "INNER JOIN comment c ON cr.comment_id = c.comment_id " +
                "INNER JOIN member r ON cr.reactor_id = r.member_id " +
                "WHERE cr.comment_id IN (" + commentIdsStr + ")";

        MapSqlParameterSource commentReactionParameters = createCommentReactionParameters(commentIds);

        jdbcTemplate.query(findCommentReactionsNativeQuery, commentReactionParameters, rs -> {
            Long bookmarkId = rs.getLong("bookmark_id");
            ReadCommentSliceResponse commentSlice = commentSliceMap.get(bookmarkId);

            Long commentId = rs.getLong("comment_id");
            String reactorNickname = rs.getString("nickname");
            String reactionName = rs.getString("reaction");
            commentSlice.addCommentReaction(commentId, reactorNickname, reactionName);
        });
    }

    private MapSqlParameterSource createCommentSliceParameters(List<Long> bookmarkIds, Integer size) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("size", size + 1);
        for (int i = 1; i <= bookmarkIds.size(); i++) {
            parameters.addValue("bookmarkId" + i, bookmarkIds.get(i - 1));
        }
        return parameters;
    }

    private boolean isLastComment(ResultSet rs, Integer size, ReadCommentSliceResponse commentSlice) throws SQLException {
        int rowNum = rs.getInt("row_num");
        if (rowNum > size) {
            commentSlice.markAsHasNext();
            return true;
        }
        return false;
    }

    private void addCommentToCommentSlice(ResultSet rs, ReadCommentSliceResponse commentSlice, List<Long> commentIds) throws SQLException {
        Long commentId = rs.getLong("comment_id");
        Long writerId = rs.getLong("writer_id");
        String writerNickname = rs.getString("writer_nickname");
        String writerProfileImageUrl = rs.getString("writer_profile_image_url");
        String contents = rs.getString("contents");
        Instant createdTime = rs.getTimestamp("created_time").toInstant();

        commentSlice.addComment(commentId, writerId, writerNickname, writerProfileImageUrl, createdTime, contents);
        commentIds.add(commentId);
    }

    private MapSqlParameterSource createCommentReactionParameters(List<Long> commentIds) {
        MapSqlParameterSource commentReactionParameters = new MapSqlParameterSource();
        for (int i = 1; i <= commentIds.size(); i++) {
            commentReactionParameters.addValue("commentId" + i, commentIds.get(i - 1));
        }
        return commentReactionParameters;
    }

}
