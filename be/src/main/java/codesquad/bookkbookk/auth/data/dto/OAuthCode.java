package codesquad.bookkbookk.auth.data.dto;

import java.net.URLDecoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthCode {

    @JsonProperty("oAuthCode")
    private String oAuthCode;

    @Builder
    private OAuthCode(String oAuthCode) {
        this.oAuthCode = oAuthCode;
    }

    public String getDecodedOAuthCode() {
        return URLDecoder.decode(oAuthCode);
    }

}
