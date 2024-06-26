package codesquad.bookkbookk.domain.mapping.repository;

import static codesquad.bookkbookk.domain.mapping.entity.QBookmarkReaction.*;
import static codesquad.bookkbookk.domain.member.data.entity.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookmarkReactionCustomRepositoryImpl implements BookmarkReactionCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BookmarkReaction> findAllByBookmarkId(Long bookmarkId) {
        return jpaQueryFactory
                .selectFrom(bookmarkReaction)
                .innerJoin(bookmarkReaction.reactor, member).fetchJoin()
                .where(bookmarkReaction.bookmark.id.eq(bookmarkId))
                .fetch();
    }

}
