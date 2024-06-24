package codesquad.bookkbookk.domain.bookclub.repository;

import static codesquad.bookkbookk.domain.bookclub.data.entity.QBookClub.*;
import static codesquad.bookkbookk.domain.mapping.entity.QBookClubMember.*;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookClubCustomRepositoryImpl implements BookClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BookClub> findAllByMemberIdAndStatus(Long memberId, BookClubStatus bookClubStatus) {
        List<BookClub> bookClubs = jpaQueryFactory
                .selectFrom(bookClub)
                .innerJoin(bookClub.bookClubMembers, bookClubMember)
                .where(bookClubMember.member.id.eq(memberId),
                        createBookClubStatusCondition(bookClubStatus))
                .fetch();
        bookClubs.stream().map(BookClub::getBooks).forEach(Hibernate::initialize);
        bookClubs.stream().map(BookClub::getBookClubMembers).forEach(Hibernate::initialize);

        return bookClubs;
    }

    @Override
    public Optional<BookClub> findDetailById(Long bookClubId) {
        Optional<BookClub> optional = Optional.ofNullable(jpaQueryFactory
                .selectFrom(bookClub)
                .where(bookClub.id.eq(bookClubId))
                .fetchOne());
        if (optional.isPresent()) {
            optional.get().getBooks().forEach(Hibernate::initialize);
            optional.get().getBookClubMembers().forEach(Hibernate::initialize);
        }
        return optional;
    }

    private BooleanExpression createBookClubStatusCondition(BookClubStatus bookClubStatus) {
        if (bookClubStatus == null) {
            return null;
        }
        return bookClub.status.eq(bookClubStatus);
    }

}
