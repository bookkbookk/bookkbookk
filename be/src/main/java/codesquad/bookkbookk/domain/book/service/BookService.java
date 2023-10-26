package codesquad.bookkbookk.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookResponse;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.data.entity.MemberBook;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.book.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookClubRepository bookClubRepository;
    private final MemberBookClubRepository memberBookClubRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    public CreateBookResponse createBook(Long memberId, CreateBookRequest request) {
        if (!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, request.getBookClubId())) {
            throw new MemberNotInBookClubException();
        }

        BookClub bookclub = bookClubRepository.findById(request.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);
        Book book = Book.of(request, bookclub);
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

}
