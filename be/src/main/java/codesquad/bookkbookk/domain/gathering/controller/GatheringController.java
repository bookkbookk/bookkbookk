package codesquad.bookkbookk.domain.gathering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UpdateGatheringResponse> updateGathering(@PathVariable Long gatheringId,
                                                                   @RequestBody UpdateGatheringRequest request) {
        UpdateGatheringResponse response = gatheringService.updateGathering(gatheringId, request);

        return ResponseEntity.ok()
                .body(response);
    }

}
