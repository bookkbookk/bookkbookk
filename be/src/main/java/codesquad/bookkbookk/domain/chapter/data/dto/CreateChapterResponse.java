package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.Getter;

@Getter
public class CreateChapterResponse {

    private final List<Long> createdChapterIds;

    public CreateChapterResponse(List<Long> createdChapterIds) {
        this.createdChapterIds = createdChapterIds;
    }

    public static CreateChapterResponse from(List<Chapter> chapters) {
        List<Long> chapterIds = chapters.stream()
                .map(Chapter::getId)
                .collect(Collectors.toUnmodifiableList());

        return new CreateChapterResponse(chapterIds);
    }

}
