package codesquad.bookkbookk.domain.comment.repository;

import static codesquad.bookkbookk.domain.comment.data.entity.QComment.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.comment.data.entity.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findAllByBookmarkId(Long bookmarkId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .innerJoin(comment.writer).fetchJoin()
                .where(comment.bookmark.id.eq(bookmarkId))
                .fetch();
    }

}
