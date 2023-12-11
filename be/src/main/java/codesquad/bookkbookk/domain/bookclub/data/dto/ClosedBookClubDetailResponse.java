package codesquad.bookkbookk.domain.bookclub.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClosedBookClubDetailResponse extends ReadBookClubDetailResponse{

    private final LocalDateTime closedTime;

    @Builder
    private ClosedBookClubDetailResponse(String name, BookClubStatus status, String profileImgUrl,
                                         LocalDateTime createdTime, ReadBookClubLastBook readBookClubLastBook,
                                         List<ReadBookClubMember> readBookClubMembers,
                                         LocalDateTime closedTime) {
        super(name, status, profileImgUrl, createdTime, readBookClubLastBook, readBookClubMembers);
        this.closedTime = closedTime;
    }

    public static ClosedBookClubDetailResponse from(BookClub bookClub) {
        Book lastBook = bookClub.getBooks().get(bookClub.getBooks().size() - 1);
        List<Member> members = BookClubMember.toMembers(bookClub.getBookClubMembers());

        return ClosedBookClubDetailResponse.builder()
                .name(bookClub.getName())
                .status(bookClub.getBookClubStatus())
                .profileImgUrl(bookClub.getProfileImgUrl())
                .createdTime(bookClub.getCreatedTime())
                .readBookClubLastBook(ReadBookClubLastBook.from(lastBook))
                .readBookClubMembers(ReadBookClubMember.from(members))
                .closedTime(bookClub.getClosedTime())
                .build();
    }

    public static List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
        return bookClubs.stream()
                .map(ClosedBookClubDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}
