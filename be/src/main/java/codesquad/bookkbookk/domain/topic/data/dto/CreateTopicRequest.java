package codesquad.bookkbookk.domain.topic.data.dto;

import lombok.Getter;

@Getter
public class CreateTopicRequest {

    private final Long chapterId;
    private final String title;

    public CreateTopicRequest(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

}
