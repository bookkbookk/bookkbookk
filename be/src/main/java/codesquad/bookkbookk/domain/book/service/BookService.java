package codesquad.bookkbookk.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookClubBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusRequest;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusResponse;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.entity.MemberBook;
import codesquad.bookkbookk.domain.mapping.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorizationService authorizationService;

    private final BookRepository bookRepository;
    private final BookClubRepository bookClubRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    public CreateBookResponse createBook(Long memberId, CreateBookRequest request) {
        authorizationService.authorizeBookClubMembershipByBookClubId(memberId, request.getBookClubId());

        BookClub bookclub = bookClubRepository.findById(request.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .bookClub(bookclub)
                .title(request.getTitle())
                .cover(request.getCover())
                .author(request.getAuthor())
                .category(request.getCategory())
                .build();

        bookRepository.save(book);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        MemberBook memberBook = new MemberBook(member, book);
        memberBookRepository.save(memberBook);

        return new CreateBookResponse(book.getId());
    }

    public ReadBookResponse readBooks(Long memberId, Pageable pageable) {
        Page<Book> books = bookRepository.findBooksByMemberId(memberId, pageable);

        return ReadBookResponse.from(books);
    }

    public ReadBookClubBookResponse readBookClubBooks(Long memberId, Long bookClubId, Pageable pageable) {
        authorizationService.authorizeBookClubMembershipByBookClubId(memberId, bookClubId);

        Slice<Book> books = bookRepository.findBooksByBookClubId(bookClubId, pageable);
        return ReadBookClubBookResponse.from(books);
    }

    @Transactional
    public UpdateBookStatusResponse updateBookStatus(Long memberId, Long bookId, UpdateBookStatusRequest request) {
        authorizationService.authorizeBookClubMembershipByBookId(memberId, bookId);

        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        book.updateStatus(request);
        return UpdateBookStatusResponse.from(book);
    }

}
