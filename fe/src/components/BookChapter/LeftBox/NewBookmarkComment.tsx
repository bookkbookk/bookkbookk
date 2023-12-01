import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { BookmarkCommentProvider } from "context/BookmarkComment/BookmarkCommentProvider";

export default function NewBookmarkComment({
  bookmarkId,
  toggleReplying,
}: {
  bookmarkId: number;
  toggleReplying: () => void;
}) {
  const targetRef = useAutoScroll();

  return (
    <BookmarkCommentProvider>
      <CommentTextarea>
        <CommentTextarea.Comment {...{ bookmarkId, toggleReplying }} />
      </CommentTextarea>
      <Target ref={targetRef} />
    </BookmarkCommentProvider>
  );
}
