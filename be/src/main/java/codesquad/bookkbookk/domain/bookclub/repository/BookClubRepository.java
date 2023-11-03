package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

public interface BookClubRepository extends JpaRepository<BookClub, Long> {

    @Query("SELECT bookclub FROM BookClub bookclub " +
            "JOIN member_book_club member_book_club " +
            "ON bookclub.id = member_book_club.bookClub.id " +
            "WHERE member_book_club.member.id = :memberId")
    List<BookClub> findBookClubsByMemberId(Long memberId);

}
