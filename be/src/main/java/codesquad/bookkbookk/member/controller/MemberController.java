package codesquad.bookkbookk.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.data.dto.UpdateNicknameRequest;
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

    @PatchMapping("/nickname")
    public ResponseEntity<String> updateNickname(HttpServletRequest httpServletRequest,
                                                 @RequestBody UpdateNicknameRequest updateNicknameRequest) {
        String accessToken = extractAccessToken(httpServletRequest);
        Long memberId = jwtProvider.extractMemberId(accessToken);

        memberService.updateNickname(memberId, updateNicknameRequest);

        return ResponseEntity.ok().build();
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

}
