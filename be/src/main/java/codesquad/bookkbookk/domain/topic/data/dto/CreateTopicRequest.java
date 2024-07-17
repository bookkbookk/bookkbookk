package codesquad.bookkbookk.domain.topic.data.dto;

import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTopicRequest {

    private Long chapterId;
    private String title;

    public Topic toTopic(Chapter chapter) {
        return new Topic(chapter, title);
    }

}
