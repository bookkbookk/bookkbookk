package codesquad.bookkbookk.domain.bookmark.data.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkFilter {

    private final Integer startPage;
    private final Integer endPage;
    private final Instant startTime;
    private final Instant endTime;

    @Builder
    private BookmarkFilter(Integer startPage, Integer endPage, Instant startTime, Instant endTime) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
