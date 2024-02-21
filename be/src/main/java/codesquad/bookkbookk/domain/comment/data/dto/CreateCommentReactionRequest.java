package codesquad.bookkbookk.domain.comment.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateCommentReactionRequest {

    private String reactionName;

}
