import { usePatchComment } from "@api/comments/queries";
import { Comment as CommentType } from "@api/comments/type";
import { Comment } from "@components/common/Comment";
import { useCommentListActions } from "context/CommentList/useCommentList";
import { useState } from "react";

export function BookmarkComment({ comment }: { comment: CommentType }) {
  const { commentId, author, createdTime, content } = comment;

  const [updatedContent, setUpdatedContent] = useState(content);
  const onCommentContentChange = (content: string) => {
    setUpdatedContent(content);
  };

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const { setContent } = useCommentListActions();
  const { onPatchComment } = usePatchComment({
    commentId,
    onSuccessCallback: ({ updatedContent }) => {
      toggleEditing();
      setContent({ commentId, newContent: updatedContent });
    },
  });

  const patchBookmark = () => {
    if (content === updatedContent) {
      toggleEditing();
      return;
    }

    onPatchComment({ content: updatedContent });
  };

  const cancelEditing = () => {
    setUpdatedContent(content);
    toggleEditing();
  };

  // TODO: 댓글 삭제 요청

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, toggleEditing, isEditing }}
        onCancelClick={cancelEditing}
        onCompleteClick={patchBookmark}
      />
      {isEditing ? (
        <Comment.ContentEditor
          content={updatedContent}
          onChange={onCommentContentChange}
        />
      ) : (
        <Comment.ContentViewer content={content} />
      )}
      <Comment.ActionFooter />
    </Comment>
  );
}
