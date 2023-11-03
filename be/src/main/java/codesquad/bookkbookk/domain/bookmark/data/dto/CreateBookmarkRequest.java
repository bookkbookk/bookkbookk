package codesquad.bookkbookk.domain.bookmark.data.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CreateBookmarkRequest {

    private Long topicId;
    private String title;
    private String content;

}
