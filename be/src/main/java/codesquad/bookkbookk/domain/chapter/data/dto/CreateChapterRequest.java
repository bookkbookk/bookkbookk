package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public List<Chapter> toChaptersAndTopics(Book book) {
        return chapters.stream()
                .map(requestChapter -> {
                    Chapter chapter = new Chapter(book, requestChapter.title);

                    List<Topic> topics = requestChapter.topics.stream()
                            .map(requestTopic -> new Topic(chapter, requestTopic.title))
                            .collect(Collectors.toUnmodifiableList());
                    chapter.addTopics(topics);

                    return chapter;
                })
                .collect(Collectors.toUnmodifiableList());
    }

}
