package codesquad.bookkbookk.oauth.data.type;

import java.util.Map;

import codesquad.bookkbookk.oauth.data.dto.LoginRequest;

public enum LoginType {

    GOOGLE("google") {
        @Override
        public LoginRequest of(Map<String, Object> userInfos) {
            return LoginRequest.builder()
                    .email((String) userInfos.get("email"))
                    .loginType(this)
                    .nickname((String) userInfos.get("name"))
                    .profileImageUrl((String) userInfos.get("picture"))
                    .build();
        }
    };

    public final String providerName;

    LoginType(String providerName) {
        this.providerName = providerName;
    }

    public static LoginType getLoginTypeOf(String providerName) {
        return LoginType.valueOf(providerName.toUpperCase());
    }

    public abstract LoginRequest of(Map<String, Object> userInfos);

}
