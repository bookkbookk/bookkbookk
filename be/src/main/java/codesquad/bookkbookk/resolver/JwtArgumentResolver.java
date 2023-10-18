package codesquad.bookkbookk.resolver;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import codesquad.bookkbookk.jwt.JwtProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class) && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return jwtProvider.extractMemberId(jwtProvider.getToken(
                Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class))));
    }
}
