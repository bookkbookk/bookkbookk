import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { NewBookmarkProvider } from "context/NewBookmark/NewBookmarkProvider";

export default function NewBookmark({
  topicId,
  toggleNewBookmark,
}: {
  topicId: number;
  toggleNewBookmark: () => void;
}) {
  const targetRef = useAutoScroll();

  return (
    <NewBookmarkProvider>
      <CommentTextarea>
        <CommentTextarea.PageField />
        <CommentTextarea.Bookmark {...{ topicId, toggleNewBookmark }} />
      </CommentTextarea>
      <Target ref={targetRef} />
    </NewBookmarkProvider>
  );
}
