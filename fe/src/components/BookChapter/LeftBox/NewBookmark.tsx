import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { BookmarkProvider } from "context/BookmarkProvider/BookmarkProvider";

export default function NewBookmark({
  topicId,
  toggleNewBookmark,
}: {
  topicId: number;
  toggleNewBookmark: () => void;
}) {
  const targetRef = useAutoScroll();

  return (
    <BookmarkProvider>
      <CommentTextarea>
        <CommentTextarea.PageField />
        <CommentTextarea.Bookmark {...{ topicId, toggleNewBookmark }} />
      </CommentTextarea>
      <Target ref={targetRef} />
    </BookmarkProvider>
  );
}
