package codesquad.bookkbookk.domain.comment.repository;

import java.util.List;

import codesquad.bookkbookk.domain.comment.data.entity.Comment;

public interface CommentCustomRepository {

    List<Comment> findAllByBookmarkId(Long bookmarkId);

}
