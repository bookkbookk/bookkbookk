package codesquad.bookkbookk.domain.mapping.repository;

import java.util.List;

import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;

public interface CommentReactionCustomRepository {

    List<CommentReaction> findAllByCommentId(Long commentId);

}
