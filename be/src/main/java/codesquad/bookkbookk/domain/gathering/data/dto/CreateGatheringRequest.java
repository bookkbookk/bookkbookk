package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateGatheringRequest {

    private Long bookId;
    private List<CreateGatheringRequestGathering> gatherings;

    public CreateGatheringRequest(Long bookId, List<Map<String, String>> gatherings) {
        this.bookId = bookId;
        this.gatherings = gatherings.stream()
                .map(map -> new CreateGatheringRequestGathering(map.get("place"), (map.get("dateTime"))))
                .collect(Collectors.toUnmodifiableList());
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreateGatheringRequestGathering {

        private String place;
        private Instant dateTime;

        CreateGatheringRequestGathering(String place, String dateTIme) {
            this.place = place;
            this.dateTime = Instant.parse(dateTIme);
        }

    }

}
