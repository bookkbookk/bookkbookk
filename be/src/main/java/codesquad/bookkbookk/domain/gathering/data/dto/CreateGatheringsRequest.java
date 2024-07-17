package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateGatheringsRequest {

    private Long bookId;
    private List<RequestGathering> gatherings;

    public CreateGatheringsRequest(Long bookId, List<Map<String, String>> gatherings) {
        this.bookId = bookId;
        this.gatherings = gatherings.stream()
                .map(map -> new RequestGathering(map.get("place"), (map.get("dateTime"))))
                .collect(Collectors.toUnmodifiableList());
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class RequestGathering {

        private String place;
        private Instant dateTime;

        RequestGathering(String place, String dateTIme) {
            this.place = place;
            this.dateTime = Instant.parse(dateTIme);
        }

    }

    public List<Gathering> toGatherings(Book book) {
        return gatherings.stream()
                .map(requestGathering -> new Gathering(book, requestGathering.dateTime, requestGathering.place))
                .collect(Collectors.toUnmodifiableList());
    }

}
