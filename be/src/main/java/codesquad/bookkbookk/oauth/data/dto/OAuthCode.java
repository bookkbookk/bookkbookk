package codesquad.bookkbookk.oauth.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthCode {

    private String oAuthCode;

    @Builder
    private OAuthCode(String oAuthCode) {
        this.oAuthCode = oAuthCode;
    }

}
