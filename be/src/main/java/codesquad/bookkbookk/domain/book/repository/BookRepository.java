package codesquad.bookkbookk.domain.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import codesquad.bookkbookk.domain.book.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book FROM Book book " +
            "JOIN MemberBook AS memberBook " +
            "ON book.id = memberBook.book.id " +
            "WHERE memberBook.member.id = :memberId")
    Page<Book> findBooksByMemberId(Long memberId, Pageable pageable);
    Slice<Book> findBooksByBookClubId(Long bookClubId, Pageable pageable);

}
