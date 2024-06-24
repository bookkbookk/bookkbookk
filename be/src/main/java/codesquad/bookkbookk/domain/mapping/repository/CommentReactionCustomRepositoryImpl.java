package codesquad.bookkbookk.domain.mapping.repository;

import static codesquad.bookkbookk.domain.mapping.entity.QCommentReaction.*;
import static codesquad.bookkbookk.domain.member.data.entity.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentReactionCustomRepositoryImpl implements CommentReactionCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentReaction> findAllByCommentId(Long commentId) {
        return jpaQueryFactory
                .selectFrom(commentReaction)
                .innerJoin(commentReaction.reactor, member).fetchJoin()
                .where(commentReaction.comment.id.eq(commentId))
                .fetch();
    }

}
