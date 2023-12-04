import { usePatchComment } from "@api/comments/queries";
import { Comment as CommentType } from "@api/comments/type";
import { Comment } from "@components/common/Comment";
import { useState } from "react";

export function BookmarkComment({ comment }: { comment: CommentType }) {
  const { commentId, author, createdTime, content } = comment;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  // TODO: 댓글 수정 요청
  const { onPatchComment } = usePatchComment({
    commentId,
    onSuccessCallback: toggleEditing,
  });

  const patchBookmark = () => {
    onPatchComment({ content });
  };
  // TODO: 댓글 삭제 요청

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, toggleEditing, isEditing }}
        onCompleteClick={patchBookmark}
      />
      <Comment.Content {...{ content, isEditing }} />
      <Comment.Footer />
    </Comment>
  );
}
