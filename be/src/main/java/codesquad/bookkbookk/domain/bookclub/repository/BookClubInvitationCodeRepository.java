package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;

public interface BookClubInvitationCodeRepository extends JpaRepository<BookClubInvitationCode, Long> {

    Optional<BookClubInvitationCode> findByBookClubId(Long bookClubId);
    Optional<BookClubInvitationCode> findByInvitationCode(String InvitationCode);

}
