package codesquad.bookkbookk.common.resolver;

import java.util.Objects;

import javax.persistence.Access;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import codesquad.bookkbookk.common.jwt.JwtProvider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Access.class) && parameter.getParameterType().equals(String.class);
    }

    @Override
    public String resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return jwtProvider.getToken(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
    }

}
