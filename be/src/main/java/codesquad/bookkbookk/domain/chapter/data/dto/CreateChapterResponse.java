package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CreateChapterResponse {

    private final List<Long> createdChapterIds;

    public CreateChapterResponse(List<Long> createdChapterIds) {
        this.createdChapterIds = createdChapterIds;
    }

}
