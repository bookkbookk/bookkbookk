package codesquad.bookkbookk.domain.comment.repository;

import java.util.List;
import java.util.Map;

import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentSliceResponse;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;

public interface CommentCustomRepository {

    List<Comment> findAllByBookmarkId(Long bookmarkId);

    Map<Long, ReadCommentSliceResponse> findSlicesByBookmarkIds(List<Long> bookmarkIds, Integer size);

}
