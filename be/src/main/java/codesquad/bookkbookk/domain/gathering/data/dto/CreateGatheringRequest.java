package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateGatheringRequest {

    private Long bookId;
    private String place;
    private LocalDateTime dateTime;

}
