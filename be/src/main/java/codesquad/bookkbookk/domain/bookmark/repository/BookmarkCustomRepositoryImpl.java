package codesquad.bookkbookk.domain.bookmark.repository;

import static codesquad.bookkbookk.domain.book.data.entity.QBook.*;
import static codesquad.bookkbookk.domain.bookmark.data.entity.QBookmark.*;
import static codesquad.bookkbookk.domain.chapter.data.entity.QChapter.*;
import static codesquad.bookkbookk.domain.member.data.entity.QMember.*;
import static codesquad.bookkbookk.domain.topic.data.entity.QTopic.*;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.bookmark.data.dto.BookmarkFilter;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Bookmark> findAllByFilter(Long bookId, BookmarkFilter bookmarkFilter) {
                return jpaQueryFactory
                .selectFrom(bookmark)
                .innerJoin(bookmark.writer, member).fetchJoin()
                .innerJoin(bookmark.topic, topic)
                .innerJoin(topic.chapter, chapter)
                .innerJoin(chapter.book, book)
                .where(book.id.eq(bookId),
                        createPageCondition(bookmarkFilter),
                        createTimeCondition(bookmarkFilter))
                .orderBy(createOrder(bookmarkFilter))
                .fetch();
    }

    @Override
    public List<Bookmark> findAllByTopicId(Long topicId) {
        return jpaQueryFactory
                .selectFrom(bookmark)
                .innerJoin(bookmark.writer, member).fetchJoin()
                .where(bookmark.topic.id.eq(topicId))
                .fetch();
    }

    @Override
    public Slice<Bookmark> findSliceByTopicId(Long topicId, Pageable pageable) {
        List<Bookmark> bookmarks = jpaQueryFactory
                .selectFrom(bookmark)
                .innerJoin(bookmark.writer).fetchJoin()
                .where(bookmark.topicId.eq(topicId))
                .orderBy(bookmark.createdTime.desc(), bookmark.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        bookmarks.stream()
                .flatMap(bookmark -> bookmark.getBookmarkReactions().stream())
                .forEach(bookmarkReaction -> {
                    Hibernate.initialize(bookmarkReaction);
                    Hibernate.initialize(bookmarkReaction.getReactor());
                });

        boolean hasNext = bookmarks.size() > pageable.getPageSize();

        if (hasNext) {
            bookmarks.remove(bookmarks.size() - 1);
        }

        return new SliceImpl<>(bookmarks, pageable, hasNext);
    }

    private BooleanExpression createPageCondition(BookmarkFilter bookmarkFilter) {
        if (bookmarkFilter.getStartTime() != null && bookmarkFilter.getEndTime() != null) {
            return bookmark.updatedTime.between(bookmarkFilter.getStartTime(), bookmarkFilter.getEndTime());
        }
        if (bookmarkFilter.getStartTime() != null) {
            return bookmark.updatedTime.goe(bookmarkFilter.getStartTime());
        }
        if (bookmarkFilter.getEndTime() != null) {
            return bookmark.updatedTime.loe(bookmarkFilter.getEndTime());
        }
        return null;
    }

    private BooleanExpression createTimeCondition(BookmarkFilter bookmarkFilter) {
        if (bookmarkFilter.getStartPage() != null && bookmarkFilter.getEndPage() != null) {
            return bookmark.page.between(bookmarkFilter.getStartPage(), bookmarkFilter.getEndPage());
        }
        if (bookmarkFilter.getStartPage() != null) {
            return bookmark.page.goe(bookmarkFilter.getStartPage());
        }
        if (bookmarkFilter.getEndPage() != null) {
            return bookmark.page.loe(bookmarkFilter.getEndPage());
        }
        return null;
    }

    private OrderSpecifier createOrder(BookmarkFilter bookmarkFilter) {
        if (bookmarkFilter.getStartTime() == null && bookmarkFilter.getEndTime() == null &&
                (bookmarkFilter.getStartPage() != null || bookmarkFilter.getEndPage() != null)) {
            return bookmark.page.asc();
        } else {
            return bookmark.updatedTime.desc();
        }
    }

}
