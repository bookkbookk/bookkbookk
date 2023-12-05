import { usePatchComment } from "@api/comments/queries";
import { Comment as CommentType } from "@api/comments/type";
import { Comment } from "@components/common/Comment";
import { useState } from "react";

export function BookmarkComment({ comment }: { comment: CommentType }) {
  const { commentId, author, createdTime, content } = comment;

  const [updatedContent, setUpdatedContent] = useState(content);
  const onCommentContentChange = (content: string) => {
    setUpdatedContent(content);
  };

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const { onPatchComment } = usePatchComment({
    commentId,
    onSuccessCallback: ({ updatedContent }) => {
      toggleEditing();
      // TODO: 전역 상태 동기화
    },
  });

  const patchBookmark = () => {
    if (content === updatedContent) {
      toggleEditing();
      return;
    }

    onPatchComment({ content });
  };
  // TODO: 댓글 삭제 요청

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, toggleEditing, isEditing }}
        onCancelClick={toggleEditing}
        onCompleteClick={patchBookmark}
      />
      {isEditing ? (
        <Comment.Editor
          content={updatedContent}
          onChange={onCommentContentChange}
        />
      ) : (
        <Comment.Viewer content={content} />
      )}
      <Comment.Footer />
    </Comment>
  );
}
