package codesquad.bookkbookk.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.bookclub.data.entity.BookClub;

public interface BookClubRepository extends JpaRepository<BookClub, Long> {

}
