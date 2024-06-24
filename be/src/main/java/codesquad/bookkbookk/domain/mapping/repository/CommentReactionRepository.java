package codesquad.bookkbookk.domain.mapping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long>, CommentReactionCustomRepository {

    boolean existsByCommentIdAndReactorIdAndReaction(Long commentId, Long reactorId, Reaction reaction);

    Optional<CommentReaction> findByCommentIdAndReactorIdAndReaction(Long commentId, Long reactorId, Reaction reaction);
}
