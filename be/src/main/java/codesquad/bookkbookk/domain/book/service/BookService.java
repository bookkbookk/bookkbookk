package codesquad.bookkbookk.domain.book.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.data.entity.BookClubBook;
import codesquad.bookkbookk.domain.book.repository.BookClubBookRepository;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookClubBookRepository bookClubBookRepository;
    private final BookClubRepository bookClubRepository;
    private final MemberBookClubRepository memberBookClubRepository;

    public void createBook(Long memberId, CreateBookRequest request) {
        if(!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, request.getBookClubId())){
            throw new MemberNotInBookClubException();
        }

        Book book = Book.from(request);
        bookRepository.save(book);

        BookClub bookClub = bookClubRepository.findById(memberId).orElseThrow();
        BookClubBook bookClubBook = new BookClubBook(book, bookClub);

        bookClubBookRepository.save(bookClubBook);
    }

}
