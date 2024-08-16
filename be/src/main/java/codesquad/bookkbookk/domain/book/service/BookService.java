package codesquad.bookkbookk.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
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
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookClubRepository bookClubRepository;
    private final MemberRepository memberRepository;
    private final BookClubMemberRepository bookClubMemberRepository;

    @Transactional
    public CreateBookResponse createBook(Long memberId, CreateBookRequest request) {
        if (!bookClubMemberRepository.existsByBookClubIdAndMemberId(request.getBookClubId(), memberId)) {
            throw new MemberNotInBookClubException();
        }
        BookClub bookclub = bookClubRepository.findById(request.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);

        Book book = request.toBook(bookclub);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.addBook(book);

        bookRepository.save(book);

        return new CreateBookResponse(book.getId());
    }

    @Transactional(readOnly = true)
    public ReadBookResponse readBooks(Long memberId, Pageable pageable) {
        Page<Book> books = bookRepository.findPageByMemberId(memberId, pageable);

        return ReadBookResponse.from(books);
    }

    @Transactional(readOnly = true)
    public ReadBookClubBookResponse readBookClubBooks(Long bookClubId, Pageable pageable) {
        Slice<Book> books = bookRepository.findSliceByBookClubId(bookClubId, pageable);
        return ReadBookClubBookResponse.from(books);
    }

    @Transactional
    public UpdateBookStatusResponse updateBookStatus(Long bookId, UpdateBookStatusRequest request) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        book.updateStatus(request);
        return UpdateBookStatusResponse.from(book);
    }

}
