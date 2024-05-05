package codesquad.bookkbookk.domain.bookmark.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeleteBookmarkReactionRequest {

    private String reactionName;

}
