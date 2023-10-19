package codesquad.bookkbookk.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.core.JsonParseException;

import codesquad.bookkbookk.common.jwt.JwtProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final String[] getUrlWhiteList = new String[]{};
    private final String[] postUrlWhiteList = new String[]{"/api/members/login*"};
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (checkGetWhiteList(httpServletRequest) || checkLoginWhiteListLogin(httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        if (!doesContainToken(httpServletRequest)) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
            return;
        }

        try {
            String accessToken = jwtProvider.getToken(httpServletRequest);
            jwtProvider.extractMemberId(accessToken);
            chain.doFilter(request, response);
        } catch (JsonParseException e) {
            log.error("JsonParseException");
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value());
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.error("JwtException");
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "인증 오류");
        } catch (ExpiredJwtException e) {
            log.error("JwtTokenExpired");
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "토큰이 만료 되었습니다");
        }
    }

    private boolean checkGetWhiteList(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        return httpServletRequest.getMethod().equals("GET") && PatternMatchUtils.simpleMatch(getUrlWhiteList, url);
    }

    private boolean checkLoginWhiteListLogin(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        return httpServletRequest.getMethod().equals("POST") && PatternMatchUtils.simpleMatch(postUrlWhiteList, url);
    }

    private boolean doesContainToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization != null && authorization.startsWith("Bearer ");
    }

}
