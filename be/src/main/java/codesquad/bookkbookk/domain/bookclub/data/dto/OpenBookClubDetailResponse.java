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
public class OpenBookClubDetailResponse extends ReadBookClubDetailResponse{

    private final LocalDateTime upcomingGatheringDate;

    @Builder
    private OpenBookClubDetailResponse(String name, BookClubStatus status, String profileImgUrl,
                                         LocalDateTime createdTime, ReadBookClubDetailResponse.ReadBookClubLastBook readBookClubLastBook,
                                         List<ReadBookClubDetailResponse.ReadBookClubMember> readBookClubMembers,
                                         LocalDateTime upcomingGatheringDate) {
        super(name, status, profileImgUrl, createdTime, readBookClubLastBook, readBookClubMembers);
        this.upcomingGatheringDate = upcomingGatheringDate;
    }

    public static OpenBookClubDetailResponse from(BookClub bookClub) {
        Book lastBook;
        if (bookClub.getBooks().isEmpty()) {
            lastBook = null;
        } else lastBook = bookClub.getBooks().get(bookClub.getBooks().size() - 1);
        List<Member> members = BookClubMember.toMembers(bookClub.getBookClubMembers());

        return OpenBookClubDetailResponse.builder()
                .name(bookClub.getName())
                .status(bookClub.getBookClubStatus())
                .profileImgUrl(bookClub.getProfileImgUrl())
                .createdTime(bookClub.getCreatedTime())
                .readBookClubLastBook(ReadBookClubLastBook.from(lastBook))
                .readBookClubMembers(ReadBookClubMember.from(members))
                .upcomingGatheringDate(bookClub.getUpcomingGatheringDate())
                .build();
    }

    public static List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
        return bookClubs.stream()
                .map(OpenBookClubDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}
