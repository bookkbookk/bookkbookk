package codesquad.bookkbookk.domain.auth.data.dto;

import codesquad.bookkbookk.common.jwt.Jwt;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private final Boolean isNewMember;

    @Builder
    private LoginResponse(String accessToken, String refreshToken, Boolean isNewMember) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isNewMember = isNewMember;
    }

    public static LoginResponse of(Jwt jwt, boolean doesMemberExist) {
        return LoginResponse.builder()
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .isNewMember(!doesMemberExist)
                .build();
    }

}
