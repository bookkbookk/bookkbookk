package codesquad.bookkbookk.auth.data.provider;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProvider {

    private final Map<String, Property> properties = new HashMap<>();

    @Getter
    @RequiredArgsConstructor
    @ConstructorBinding
    public static class Property {

        private final String clientId;
        private final String clientSecret;
        private final String redirectUrl;
        private final String tokenRequestUrl;
        private final String userInfoRequestUrl;

    }

}
