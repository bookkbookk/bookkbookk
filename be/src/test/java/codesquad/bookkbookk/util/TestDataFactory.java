package codesquad.bookkbookk.util;

import codesquad.bookkbookk.auth.data.type.LoginType;
import codesquad.bookkbookk.member.data.entity.Member;

public class TestDataFactory {

    public static Member createMember() {
        return Member.builder()
                .email("nag@email.com")
                .loginType(LoginType.GOOGLE)
                .nickname("nag")
                .profileImgUrl("profile")
                .build();
    }

}
