package codesquad.bookkbookk.domain.member.data.dto;

import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponse {

    private final Long id;
    private final String nickname;
    private final String profileImgUrl;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getNickname(), member.getProfileImgUrl());
    }

}
