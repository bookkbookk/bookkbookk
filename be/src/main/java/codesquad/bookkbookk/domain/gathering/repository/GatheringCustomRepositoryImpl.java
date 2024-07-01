package codesquad.bookkbookk.domain.gathering.repository;

import static codesquad.bookkbookk.domain.book.data.entity.QBook.*;
import static codesquad.bookkbookk.domain.bookclub.data.entity.QBookClub.*;
import static codesquad.bookkbookk.domain.gathering.data.entity.QGathering.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GatheringCustomRepositoryImpl implements GatheringCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Gathering> findAllByBookClubId(Long bookClubId) {
        return jpaQueryFactory
                .selectFrom(gathering)
                .innerJoin(gathering.book, book).fetchJoin()
                .innerJoin(book.bookClub, bookClub)
                .where(bookClub.id.eq(bookClubId))
                .fetch();
    }

}
