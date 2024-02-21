package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.LocalDateTime;

import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateGatheringResponse {

    private final Long gatheringId;
    private final LocalDateTime dateTime;
    private final String place;

    public static UpdateGatheringResponse from(Gathering gathering) {
        return new UpdateGatheringResponse(gathering.getId(), gathering.getStartTime(), gathering.getPlace());
    }

}
