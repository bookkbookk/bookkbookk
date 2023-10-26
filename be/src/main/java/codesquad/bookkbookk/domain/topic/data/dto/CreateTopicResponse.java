package codesquad.bookkbookk.domain.topic.data.dto;

import lombok.Getter;

@Getter
public class CreateTopicResponse {

    private final Long createdTopicId;

    public CreateTopicResponse(Long createdTopicId) {
        this.createdTopicId = createdTopicId;
    }

}
