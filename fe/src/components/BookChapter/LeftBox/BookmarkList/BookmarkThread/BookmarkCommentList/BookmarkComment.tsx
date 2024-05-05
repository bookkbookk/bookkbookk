import { useDeleteComment, usePatchComment } from "@api/comments/queries";
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

  const { updateContent, deleteComment } = useCommentListActions();

  const { onPatchComment } = usePatchComment({
    commentId,
    onSuccessCallback: ({ updatedContent }) => {
      toggleEditing();
      updateContent({ commentId, newContent: updatedContent });
    },
  });

  const { onDeleteComment } = useDeleteComment({
    commentId,
    onSuccessCallback: () => deleteComment({ commentId }),
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

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, toggleEditing, isEditing }}
        onCancelClick={cancelEditing}
        onDeleteClick={onDeleteComment}
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
