package codesquad.bookkbookk.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.data.dto.UpdateProfileRequest;
import codesquad.bookkbookk.member.service.MemberService;
import codesquad.bookkbookk.resolver.MemberId;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> readMember(@MemberId Long memberId) {
        MemberResponse memberResponse = memberService.readMember(memberId);

        return ResponseEntity.ok()
                .body(memberResponse);
    }

    @PatchMapping("/profile")
    public ResponseEntity<String> updateProfile(@MemberId Long memberId,
                                                @ModelAttribute UpdateProfileRequest updateProfileRequest) {
        memberService.updateProfile(memberId, updateProfileRequest);

        return ResponseEntity.ok().build();
    }

}
