package codesquad.bookkbookk.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @GetMapping
    public ResponseEntity<MemberResponse> readMember(HttpServletRequest request) {
        String accessToken = extractAccessToken(request);
        Long memberId = jwtProvider.extractMemberId(accessToken);

        MemberResponse memberResponse = memberService.readMember(memberId);

        return ResponseEntity.ok()
                .body(memberResponse);

    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }
}
