package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UpdateGatheringRequest {

    private LocalDateTime dateTime;
    private String place;

}
