package codesquad.bookkbookk.bookclub.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.bookclub.data.entity.BookClub;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadBookClubResponse {

    private Long id;
    private Long creatorId;
    private String name;
    private String profileImgUrl;

    @Builder
    private ReadBookClubResponse(Long id, Long creatorId, String name, String profileImgUrl) {
        this.id = id;
        this.creatorId = creatorId;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }

    public static ReadBookClubResponse from(BookClub bookClub){
        return ReadBookClubResponse.builder()
                .id(bookClub.getId())
                .creatorId(bookClub.getCreatorId())
                .name(bookClub.getName())
                .profileImgUrl(bookClub.getProfileImgUrl())
                .build();
    }

    public static List<ReadBookClubResponse> from(List<BookClub> bookClubs){
        return bookClubs.stream()
                .map(ReadBookClubResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
