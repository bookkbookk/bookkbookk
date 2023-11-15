package codesquad.bookkbookk.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.bookkbookk.common.error.ErrorResponse;
import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.RefreshTokenNotFoundException;
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
        boolean isTokenValid = false;

        if (checkGetWhiteList(httpServletRequest) || checkLoginWhiteListLogin(httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        if (httpServletRequest.getRequestURI().equals("/api/auth/reissue")) {
            isTokenValid = validateRefreshToken(httpServletRequest, httpServletResponse);
        } else isTokenValid = validateAccessToken(httpServletRequest, httpServletResponse);

        if (isTokenValid) {
            chain.doFilter(request, response);
        }
    }

    private boolean validateAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        String authorization = httpServletRequest.getHeader("Authorization");

        if (authorization == null) {
            writeErrorResponse(new NoAuthorizationHeaderException(), httpServletResponse);
            return false;
        }
        if (!authorization.startsWith("Bearer ")) {
            writeErrorResponse(new BearerPrefixNotIncludedException(), httpServletResponse);
            return false;
        }

        String accessToken = authorization.substring("Bearer ".length());
        try {
            jwtProvider.validateToken(accessToken);
        } catch (IllegalArgumentException e) {
            writeErrorResponse(new TokenNotIncludedException(), httpServletResponse);
            return false;
        } catch (MalformedJwtException | SecurityException e) {
            writeErrorResponse(new MalformedTokenException(), httpServletResponse);
            return false;
        } catch (ExpiredJwtException e) {
            writeErrorResponse(new TokenExpiredException(), httpServletResponse);
            return false;
        }
        return true;
    }

    private boolean validateRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Cookie[] cookies = httpServletRequest.getCookies();
        String refreshToken = null;

        if (cookies == null) {
            writeErrorResponse(new RefreshTokenNotFoundException(), httpServletResponse);
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        try {
            jwtProvider.validateToken(refreshToken);
        } catch (IllegalArgumentException e) {
            writeErrorResponse(new TokenNotIncludedException(), httpServletResponse);
            return false;
        } catch (MalformedJwtException | SecurityException e) {
            writeErrorResponse(new MalformedTokenException(), httpServletResponse);
            return false;
        } catch (ExpiredJwtException e) {
            writeErrorResponse(new TokenExpiredException(), httpServletResponse);
            return false;
        }
        return true;
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
