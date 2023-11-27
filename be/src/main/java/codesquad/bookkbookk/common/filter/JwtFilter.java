package codesquad.bookkbookk.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.bookkbookk.common.error.ErrorResponse;
import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.jwt.MalformedTokenException;
import codesquad.bookkbookk.common.error.exception.jwt.NoAuthorizationHeaderException;
import codesquad.bookkbookk.common.error.exception.jwt.BearerPrefixNotIncludedException;
import codesquad.bookkbookk.common.error.exception.jwt.TokenExpiredException;
import codesquad.bookkbookk.common.error.exception.jwt.TokenNotIncludedException;
import codesquad.bookkbookk.common.jwt.JwtProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final String[] getUrlWhiteList = new String[]{};
    private final String[] postUrlWhiteList = new String[]{"/api/auth/login*"};
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (checkGetWhiteList(httpServletRequest) || checkLoginWhiteListLogin(httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null) {
            writeErrorResponse(new NoAuthorizationHeaderException(), httpServletResponse);
            return;
        }
        if (!authorization.startsWith("Bearer ")) {
            writeErrorResponse(new BearerPrefixNotIncludedException(), httpServletResponse);
            return;
        }

        String token = authorization.substring("Bearer ".length());
        try {
            jwtProvider.validateToken(token);
        } catch (IllegalArgumentException e) {
            writeErrorResponse(new TokenNotIncludedException(), httpServletResponse);
            return;
        } catch (MalformedJwtException | SecurityException e) {
            writeErrorResponse(new MalformedTokenException(), httpServletResponse);
            return;
        } catch (ExpiredJwtException e) {
            writeErrorResponse(new TokenExpiredException(), httpServletResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean checkGetWhiteList(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        return httpServletRequest.getMethod().equals("GET") && PatternMatchUtils.simpleMatch(getUrlWhiteList, url);
    }

    private boolean checkLoginWhiteListLogin(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        return httpServletRequest.getMethod().equals("POST") && PatternMatchUtils.simpleMatch(postUrlWhiteList, url);
    }

    private void writeErrorResponse(ApiException apiException, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        ErrorResponse errorResponse = ErrorResponse.from(apiException);
        PrintWriter writer = httpServletResponse.getWriter();

        log.error(apiException.getClass().getSimpleName() + ": " + apiException.getMessage());
        httpServletResponse.setStatus(errorResponse.getCode());
        String jsonBody = objectMapper.writeValueAsString(errorResponse);
        writer.print(jsonBody);
    }

}
