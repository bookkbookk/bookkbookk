package codesquad.bookkbookk.domain.comment.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeleteCommentReactionRequest {

    private String reactionName;

}
