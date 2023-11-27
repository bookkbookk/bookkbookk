package codesquad.bookkbookk.domain.gathering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.service.GatheringService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gatherings")
@RequiredArgsConstructor
public class GatheringController {

    private final GatheringService gatheringService;

    @PostMapping
    public ResponseEntity<Void> CreateGathering(@MemberId Long memberId,
                                                @RequestBody CreateGatheringRequest createGatheringRequest) {
        gatheringService.createGathering(createGatheringRequest, memberId);

        return ResponseEntity.ok().build();
    }

}
