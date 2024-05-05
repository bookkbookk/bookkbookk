package codesquad.bookkbookk.domain.gathering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.gathering.data.dto.UpdateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.data.dto.UpdateGatheringResponse;
import codesquad.bookkbookk.domain.gathering.service.GatheringService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gatherings")
@RequiredArgsConstructor
public class GatheringController {

    private final GatheringService gatheringService;

    @PatchMapping("/{gatheringId}")
    public ResponseEntity<UpdateGatheringResponse> updateGathering(@MemberId Long memberId,
                                                                   @PathVariable Long gatheringId,
                                                                   @RequestBody UpdateGatheringRequest request) {
        UpdateGatheringResponse response = gatheringService.updateGathering(memberId, gatheringId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{gatheringId}")
    public ResponseEntity<Void> deleteGathering(@MemberId Long memberId, @PathVariable Long gatheringId) {
        gatheringService.deleteGathering(memberId, gatheringId);

        return ResponseEntity.ok().build();
    }

}
