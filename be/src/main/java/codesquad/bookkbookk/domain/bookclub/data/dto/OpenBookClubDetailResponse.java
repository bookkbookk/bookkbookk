package codesquad.bookkbookk.domain.bookclub.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OpenBookClubDetailResponse extends ReadBookClubDetailResponse{

    private final Long id;
    private final String name;
    private final BookClubStatus status;
    private final String profileImgUrl;
    private final Instant createdTime;
    private final ReadBookClubLastBook lastBook;
    private final List<ReadBookClubMember> members;
    private final Instant upcomingGatheringDate;

    @Builder
    private OpenBookClubDetailResponse(Long id, String name, BookClubStatus status, String profileImgUrl,
                                      Instant createdTime, ReadBookClubLastBook lastBook, List<ReadBookClubMember> members, Instant upcomingGatheringDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.profileImgUrl = profileImgUrl;
        this.createdTime = createdTime;
        this.lastBook = lastBook;
        this.members = members;
        this.upcomingGatheringDate = upcomingGatheringDate;
    }

    public static OpenBookClubDetailResponse from(BookClub bookClub) {
        return OpenBookClubDetailResponse.builder()
                .id(bookClub.getId())
                .name(bookClub.getName())
                .status(bookClub.getStatus())
                .profileImgUrl(bookClub.getProfileImageUrl())
                .createdTime(bookClub.getCreatedTime())
                .lastBook(ReadBookClubLastBook.from(bookClub.getBooks()))
                .members(ReadBookClubMember.from(bookClub.getMembers()))
                .upcomingGatheringDate(bookClub.getUpcomingGatheringTime())
                .build();
    }

    public static List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
        return bookClubs.stream()
                .map(OpenBookClubDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}
