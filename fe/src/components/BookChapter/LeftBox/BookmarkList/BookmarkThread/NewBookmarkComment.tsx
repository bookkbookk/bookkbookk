import { usePostComment } from "@api/comments/queries";
import { Comment } from "@components/common/Comment";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { enqueueSnackbar } from "notistack";
import { useState } from "react";

export default function NewBookmarkComment({
  bookmarkId,
  toggleReplying,
}: {
  bookmarkId: number;
  toggleReplying: () => void;
}) {
  const targetRef = useAutoScroll();
  const [commentContent, setCommentContent] = useState("");

  const onContentChange = (content: string) => {
    setCommentContent(content);
  };

  const { onPostComment } = usePostComment({
    onSuccessCallback: toggleReplying,
  });

  const postComment = () => {
    if (!commentContent) {
      return enqueueSnackbar("댓글 내용은 필수로 입력해야 해요!", {
        variant: "error",
      });
    }

    onPostComment({ bookmarkId, content: commentContent });
  };

  return (
    <>
      <Comment>
        <Comment.ContentEditor onChange={onContentChange} />
        <Comment.ButtonFooter
          onCancelClick={toggleReplying}
          onPostClick={postComment}
        />
      </Comment>
      <Target ref={targetRef} />
    </>
  );
}
