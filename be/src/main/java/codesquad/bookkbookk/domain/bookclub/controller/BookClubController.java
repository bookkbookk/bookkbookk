package codesquad.bookkbookk.domain.bookclub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.service.BookClubService;
import codesquad.bookkbookk.common.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-clubs")
public class BookClubController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final BookClubService bookClubService;

    @PostMapping
    public ResponseEntity<CreateBookClubResponse> createBookClub(HttpServletRequest httpServletRequest,
                                                                 @ModelAttribute CreateBookClubRequest request) {
        String accessToken = extractAccessToken(httpServletRequest);
        Long memberId = jwtProvider.extractMemberId(accessToken);

        CreateBookClubResponse response = bookClubService.createBookClub(memberId, request);

        return ResponseEntity.ok()
                .body(response);

    }

    @GetMapping
    public ResponseEntity<List<ReadBookClubResponse>> readBookClubs(HttpServletRequest httpServletRequest) {
        String accessToken = extractAccessToken(httpServletRequest);
        Long memberId = jwtProvider.extractMemberId(accessToken);

        List<ReadBookClubResponse> response = bookClubService.readBookClubs(memberId);

        return ResponseEntity.ok()
                .body(response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

}
