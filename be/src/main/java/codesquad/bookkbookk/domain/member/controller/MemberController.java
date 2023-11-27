package codesquad.bookkbookk.domain.member.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.domain.book.data.dto.ReadBookResponse;
import codesquad.bookkbookk.domain.book.service.BookService;
import codesquad.bookkbookk.domain.member.data.dto.MemberResponse;
import codesquad.bookkbookk.domain.member.data.dto.UpdateProfileRequest;
import codesquad.bookkbookk.domain.member.data.dto.UpdateProfileResponse;
import codesquad.bookkbookk.domain.member.service.MemberService;
import codesquad.bookkbookk.common.resolver.MemberId;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<MemberResponse> readMember(@MemberId Long memberId) {
        MemberResponse memberResponse = memberService.readMember(memberId);

        return ResponseEntity.ok()
                .body(memberResponse);
    }

    @PatchMapping("/profile")
    public ResponseEntity<UpdateProfileResponse> updateProfile(@MemberId Long memberId,
                                                               @ModelAttribute UpdateProfileRequest request) {
        UpdateProfileResponse response = memberService.updateProfile(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/books")
    public ResponseEntity<ReadBookResponse> readBooks(@MemberId Long memberId, Pageable pageable) {
        ReadBookResponse response = bookService.readBooks(memberId, pageable);

        return ResponseEntity.ok()
                .body(response);
    }

}
