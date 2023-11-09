package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

public interface BookClubRepository extends JpaRepository<BookClub, Long> {

    @Query("SELECT bookclub FROM BookClub bookclub " +
            "JOIN book_club_member book_club_member " +
            "ON bookclub.id = book_club_member.bookClub.id " +
            "WHERE book_club_member .member.id = :memberId")
    List<BookClub> findBookClubsByMemberId(Long memberId);

}
