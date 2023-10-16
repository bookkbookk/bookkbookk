package codesquad.bookkbookk.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Jwt {

    private final String accessToken;
    private final String refreshToken;

    @Builder
    private Jwt(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
