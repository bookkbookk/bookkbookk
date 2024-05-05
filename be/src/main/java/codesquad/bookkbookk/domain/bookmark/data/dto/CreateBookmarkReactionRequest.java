package codesquad.bookkbookk.domain.bookmark.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateBookmarkReactionRequest {

    private String reactionName;

}
