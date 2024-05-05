package codesquad.bookkbookk.domain.auth.data.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "cookie")
@ConstructorBinding
public class CookieProperty {

    private final String domain;
    private final boolean secure;
}
