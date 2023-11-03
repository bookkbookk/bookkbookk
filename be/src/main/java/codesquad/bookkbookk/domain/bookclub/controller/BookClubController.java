package codesquad.bookkbookk.domain.bookclub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.service.BookClubInvitationService;
import codesquad.bookkbookk.domain.bookclub.service.BookClubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-clubs")
public class BookClubController {

    private final BookClubService bookClubService;
    private final BookClubInvitationService bookClubInvitationService;

    @PostMapping
    public ResponseEntity<CreateBookClubResponse> createBookClub(@MemberId Long memberId,
                                                                 @ModelAttribute CreateBookClubRequest request) {
        CreateBookClubResponse response = bookClubService.createBookClub(memberId, request);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadBookClubResponse>> readBookClubs(@MemberId Long memberId) {
        List<ReadBookClubResponse> response = bookClubService.readBookClubs(memberId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/invitation")
    public ResponseEntity<InvitationUrlResponse> createInvitationUrl(
            @MemberId Long memberId, @RequestBody CreateInvitationUrlRequest request) {
        InvitationUrlResponse response = bookClubInvitationService.createInvitationUrl(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/invitation/{bookClubId}")
    public ResponseEntity<InvitationUrlResponse> readInvitationUrl(
            @MemberId Long memberId, @PathVariable Long bookClubId) {
        InvitationUrlResponse response = bookClubInvitationService.readInvitationUrl(memberId, bookClubId);

        return ResponseEntity.ok()
                .body(response);
    }

}
