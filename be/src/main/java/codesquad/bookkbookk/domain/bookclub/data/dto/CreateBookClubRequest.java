package codesquad.bookkbookk.domain.bookclub.data.dto;

import org.springframework.web.multipart.MultipartFile;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookClubRequest {

    private String name;
    private MultipartFile profileImage;

    public CreateBookClubRequest(String name, MultipartFile profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public BookClub toBookClub(Member member, String profileImgUrl) {
        return BookClub.builder()
                .creatorId(member.getId())
                .name(name)
                .profileImageUrl(profileImgUrl)
                .build();

    }

}
