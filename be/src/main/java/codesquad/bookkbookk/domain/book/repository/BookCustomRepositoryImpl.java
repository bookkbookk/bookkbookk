package codesquad.bookkbookk.domain.book.repository;

import static codesquad.bookkbookk.domain.book.data.entity.QBook.*;
import static codesquad.bookkbookk.domain.mapping.entity.QMemberBook.*;
import static codesquad.bookkbookk.domain.member.data.entity.QMember.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.book.data.entity.Book;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Book> findPageByMemberId(Long memberId, Pageable pageable) {
        List<Book> books = jpaQueryFactory
                .selectFrom(book)
                .innerJoin(book.bookMembers, memberBook).fetchJoin()
                .innerJoin(memberBook.member, member).fetchJoin()
                .innerJoin(book.bookClub).fetchJoin()
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(book)
                .innerJoin(book.bookMembers, memberBook)
                .innerJoin(memberBook.member, member)
                .innerJoin(book.bookClub)
                .where(member.id.eq(memberId))
                .fetchCount();

        return new PageImpl<>(books, pageable, total);
    }

    @Override
    public Slice<Book> findSliceByBookClubId(Long bookClubId, Pageable pageable) {
        List<Book> books = jpaQueryFactory
                .selectFrom(book)
                .where(book.bookClub.id.eq(bookClubId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = books.size() > pageable.getPageSize();

        if (hasNext) {
            books.remove(books.size() - 1);
        }

        return new SliceImpl<>(books, pageable, hasNext);
    }

}
