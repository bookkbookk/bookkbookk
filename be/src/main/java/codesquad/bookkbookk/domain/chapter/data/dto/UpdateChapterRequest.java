package codesquad.bookkbookk.domain.chapter.data.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UpdateChapterRequest {

    private String title;
    private Integer statusId;

}
