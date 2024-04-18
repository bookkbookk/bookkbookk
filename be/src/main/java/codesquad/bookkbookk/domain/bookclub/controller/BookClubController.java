package codesquad.bookkbookk.domain.bookclub.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookClubBookResponse;
import codesquad.bookkbookk.domain.book.service.BookService;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubDetailResponse;
import codesquad.bookkbookk.domain.bookclub.service.BookClubService;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.data.dto.ReadGatheringResponse;
import codesquad.bookkbookk.domain.gathering.service.GatheringService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-clubs")
public class BookClubController {

    private final BookClubService bookClubService;
    private final BookService bookService;
    private final GatheringService gatheringService;

    @PostMapping
    public ResponseEntity<CreateBookClubResponse> createBookClub(@MemberId Long memberId,
                                                                 @ModelAttribute CreateBookClubRequest request) {
        CreateBookClubResponse response = bookClubService.createBookClub(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadBookClubDetailResponse>> readBookClubs(@MemberId Long memberId,
            @RequestParam(defaultValue = "ALL") String status) {
        List<ReadBookClubDetailResponse> response = bookClubService.readBookClubs(memberId, status);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/invitation")
    public ResponseEntity<InvitationUrlResponse> createInvitationUrl(@MemberId Long memberId,
                                                                     @RequestBody CreateInvitationUrlRequest request) {
        InvitationUrlResponse response = bookClubService.createInvitationUrl(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/invitation/{bookClubId}")
    public ResponseEntity<InvitationUrlResponse> readInvitationUrl(@MemberId Long memberId,
                                                                   @PathVariable Long bookClubId) {
        InvitationUrlResponse response = bookClubService.createInvitationUrl(memberId,
                new CreateInvitationUrlRequest(bookClubId));

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<JoinBookClubResponse> joinBookClub(@MemberId Long memberId,
                                                             @RequestBody JoinBookClubRequest joinBookClubRequest) {
        JoinBookClubResponse response = bookClubService.joinBookClub(memberId, joinBookClubRequest);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{bookClubId}/books")
    public ResponseEntity<ReadBookClubBookResponse> readBookClubBooks(@MemberId Long memberId,
                                                                      @PathVariable Long bookClubId,
                                                                      @RequestParam Integer cursor,
                                                                      @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(cursor, size);
        ReadBookClubBookResponse response = bookService.readBookClubBooks(memberId, bookClubId, pageable);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{bookClubId}")
    public ResponseEntity<ReadBookClubDetailResponse> readBookClubDetail(@MemberId Long memberId,
                                                                         @PathVariable Long bookClubId) {
        ReadBookClubDetailResponse response = bookClubService.readBookClubDetail(memberId, bookClubId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/{bookClubId}/gatherings")
    public ResponseEntity<Void> createGathering(@MemberId Long memberId, @PathVariable Long bookClubId,
                                                @RequestBody CreateGatheringRequest createGatheringRequest) {
        gatheringService.createGathering(memberId, bookClubId, createGatheringRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookClubId}/gatherings")
    public ResponseEntity<List<ReadGatheringResponse>> readGatherings(@MemberId Long memberId,
                                                                      @PathVariable Long bookClubId) {
        List<ReadGatheringResponse> responses = gatheringService.readGatherings(memberId, bookClubId);

        return ResponseEntity.ok()
                .body(responses);
    }

}
