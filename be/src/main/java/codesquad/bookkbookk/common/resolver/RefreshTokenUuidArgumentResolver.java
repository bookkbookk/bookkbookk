package codesquad.bookkbookk.common.resolver;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import codesquad.bookkbookk.common.error.exception.auth.TokenNotIncludedException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.type.TokenError;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RefreshTokenUuidArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RefreshTokenUuid.class) && parameter.getParameterType().equals(String.class);
    }

    @Override
    public String resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Cookie[] cookies = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)).getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return jwtProvider.extractUuid(cookie.getValue());
            }
        }
        throw new TokenNotIncludedException(TokenError.REFRESH_TOKEN);
    }

}
