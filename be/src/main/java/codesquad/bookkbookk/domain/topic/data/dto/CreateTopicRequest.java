package codesquad.bookkbookk.domain.topic.data.dto;

import lombok.Getter;

@Getter
public class CreateTopicRequest {

    private Long chapterId;
    private String title;

    public CreateTopicRequest(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

}
