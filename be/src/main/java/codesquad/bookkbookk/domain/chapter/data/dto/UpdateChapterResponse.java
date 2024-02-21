package codesquad.bookkbookk.domain.chapter.data.dto;

import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateChapterResponse {

    private final String title;
    private final Integer statusId;

    public static UpdateChapterResponse from(Chapter chapter) {
        return new UpdateChapterResponse(chapter.getTitle(), chapter.getStatus().getId());
    }

}
