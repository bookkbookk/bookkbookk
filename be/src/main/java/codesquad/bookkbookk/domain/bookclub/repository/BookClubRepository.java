package codesquad.bookkbookk.domain.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

public interface BookClubRepository extends JpaRepository<BookClub, Long>, BookClubCustomRepository {

    @Query("SELECT bookclub FROM BookClub bookclub " +
            "JOIN BookClubMember AS bookClubMember " +
            "ON bookclub.id = bookClubMember.bookClub.id " +
            "WHERE bookClubMember.member.id = :memberId")
    List<BookClub> findBookClubsByMemberId(Long memberId);

}
