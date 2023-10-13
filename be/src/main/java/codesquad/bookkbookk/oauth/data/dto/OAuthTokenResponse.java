package codesquad.bookkbookk.oauth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
