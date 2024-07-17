package codesquad.bookkbookk.domain.chapter.repository;

import static codesquad.bookkbookk.domain.bookmark.data.entity.QBookmark.*;
import static codesquad.bookkbookk.domain.chapter.data.entity.QChapter.*;
import static codesquad.bookkbookk.domain.topic.data.entity.QTopic.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChapterCustomRepositoryImpl implements ChapterCustomRepository {

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

    private BooleanExpression createChapterStatusCondition(Status status) {
        if (status == Status.ALL) {
            return null;
        }
        return chapter.status.eq(status);
    }

}
