package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChapterRequest {

    private Long bookId;
    private List<ChapterRequest> chapters;

    @Getter
    private static class ChapterRequest {

        private String title;
        private List<TopicRequest> topics;

    }

    @Getter
    private static class TopicRequest {

        private String title;

    }

    @Getter
    public static class ChapterDataDTO {

        private Chapter chapter;
        private List<Topic> topicList;

        public ChapterDataDTO(Chapter chapter, List<Topic> topicList) {
            this.chapter = chapter;
            this.topicList = topicList;
        }

    }

    public List<ChapterDataDTO> toEntities(Book book) {
        return chapters.stream()
                .map(chapterRequest -> {
                    Chapter chapter = new Chapter(book, chapterRequest.title);

                    List<Topic> topicList = chapterRequest.topics.stream()
                            .map(topicRequest -> new Topic(chapter, topicRequest.title))
                            .collect(Collectors.toUnmodifiableList());

                    return new ChapterDataDTO(chapter, topicList);
                })
                .collect(Collectors.toUnmodifiableList());
    }

}
