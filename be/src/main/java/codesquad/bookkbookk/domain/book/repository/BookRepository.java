package codesquad.bookkbookk.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.book.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);
}
