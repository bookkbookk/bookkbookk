package codesquad.bookkbookk.domain.topic.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.Getter;

@Getter
public class ReadTopicResponse {

    private final Long topicId;
    private final String title;

    public ReadTopicResponse(Long topicId, String title) {
        this.topicId = topicId;
        this.title = title;
    }

    public static ReadTopicResponse from(Topic topic) {
        return new ReadTopicResponse(topic.getId(), topic.getTitle());
    }

    public static List<ReadTopicResponse> from(List<Topic> topics) {
        return topics.stream()
                .map(ReadTopicResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}
