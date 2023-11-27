package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadChapterResponse {

    private final String title;
    private final int topicsCount;

    public static List<ReadChapterResponse> from(List<Chapter> chapters) {
        return chapters.stream()
                .map(ReadChapterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static ReadChapterResponse from(Chapter chapter) {
        return new ReadChapterResponse(chapter.getTitle(), chapter.getTopics().size());
    }

}
