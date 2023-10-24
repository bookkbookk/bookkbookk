package codesquad.bookkbookk.domain.bookclub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.service.BookClubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-clubs")
public class BookClubController {

    private final BookClubService bookClubService;

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

}
