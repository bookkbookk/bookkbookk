package codesquad.bookkbookk.domain.topic.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTopicRequest {

    private Long chapterId;
    private String title;

}
