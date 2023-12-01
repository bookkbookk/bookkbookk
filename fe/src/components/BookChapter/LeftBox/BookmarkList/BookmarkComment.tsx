import { Comment as CommentType } from "@api/comments/type";
import { Comment } from "@components/common/Comment";
import { useState } from "react";

export function BookmarkComment({ comment }: { comment: CommentType }) {
  const { commentId, author, createdTime, content } = comment;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  return (
    <Comment key={commentId}>
      <Comment.Header {...{ author, createdTime, toggleEditing, isEditing }} />
      <Comment.Content {...{ content, isEditing }} />
      <Comment.Footer />
    </Comment>
  );
}
