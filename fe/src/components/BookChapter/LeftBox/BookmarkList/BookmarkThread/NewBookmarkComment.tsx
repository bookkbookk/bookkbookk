import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { NewCommentProvider } from "context/NewComment/NewCommentProvider";

export default function NewBookmarkComment({
  bookmarkId,
  toggleReplying,
}: {
  bookmarkId: number;
  toggleReplying: () => void;
}) {
  const targetRef = useAutoScroll();

  return (
    <NewCommentProvider>
      <Target ref={targetRef} />
      <CommentTextarea>
        <CommentTextarea.Comment {...{ bookmarkId, toggleReplying }} />
      </CommentTextarea>
    </NewCommentProvider>
  );
}
