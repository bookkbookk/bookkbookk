package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationUrl;

public interface BookClubInvitationUrlRepository extends JpaRepository<BookClubInvitationUrl, Long> {

    Optional<BookClubInvitationUrl> findByBookClubId(Long bookClubId);

}
