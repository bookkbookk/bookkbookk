import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import {
  useNewBookmarkActions,
  useNewBookmarkState,
} from "context/NewBookmark/useNewBookmark";

export default function NewBookmark({
  topicId,
  toggleNewBookmark,
}: {
  topicId: number;
  toggleNewBookmark: () => void;
}) {
  const targetRef = useAutoScroll();

  const { page } = useNewBookmarkState();
  const { setPage } = useNewBookmarkActions();

  const onPageChange = (value: string) => {
    setPage(value);
  };

  return (
    <>
      <Target ref={targetRef} />
      <CommentTextarea>
        <CommentTextarea.PageEditor
          value={page ?? ""}
          onChange={onPageChange}
        />
        <CommentTextarea.Bookmark {...{ topicId, toggleNewBookmark }} />
      </CommentTextarea>
    </>
  );
}
