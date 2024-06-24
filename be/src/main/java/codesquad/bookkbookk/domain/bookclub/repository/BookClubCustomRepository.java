package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.List;
import java.util.Optional;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;

public interface BookClubCustomRepository {

    List<BookClub> findAllByMemberIdAndStatus(Long memberId, BookClubStatus bookClubStatus);

    Optional<BookClub> findDetailById(Long bookClubId);

}

