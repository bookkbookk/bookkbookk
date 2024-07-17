package codesquad.bookkbookk.domain.auth.data.dto;

import java.util.Map;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    private final String email;
    private final LoginType loginType;
    private final String nickname;
    private final String profileImageUrl;

    @Builder
    private LoginRequest(String email, LoginType loginType, String nickname, String profileImageUrl) {
        this.email = email;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static LoginRequest of(String providerName, Map<String, Object> userInfos) {
        LoginType loginType = LoginType.getLoginTypeOf(providerName);

        return loginType.of(userInfos);
    }

    public Member toMember() {
        return Member.builder()
                .email(email)
                .loginType(loginType)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
