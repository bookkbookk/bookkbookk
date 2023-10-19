package codesquad.bookkbookk.domain.auth.data.dto;

import java.net.URLDecoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCode {

    @JsonProperty("authCode")
    private String authCode;

    @Builder
    private AuthCode(String oAuthCode) {
        this.authCode = authCode;
    }

    public String getDecodedOAuthCode() {
        return URLDecoder.decode(authCode);
    }

}
