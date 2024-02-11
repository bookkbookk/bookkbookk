package codesquad.bookkbookk.domain.member.data.dto;

import codesquad.bookkbookk.domain.member.data.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String profileImgUrl;

    @Builder
    private MemberResponse(Long id, String email, String nickname, String profileImgUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImageUrl())
                .build();
    }

}
