package codesquad.bookkbookk.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.domain.comment.data.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
