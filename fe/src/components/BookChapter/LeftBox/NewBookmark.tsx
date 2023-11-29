import { CommentTextarea } from "@components/common/CommentTextarea";
import { NewBookmarkProvider } from "context/NewBookmark/NewBookmarkProvider";

export default function NewBookmark({ topicId }: { topicId: number }) {
  return (
    <NewBookmarkProvider topicId={topicId}>
      <CommentTextarea>
        <CommentTextarea.PageField />
        <CommentTextarea.Bookmark />
      </CommentTextarea>
    </NewBookmarkProvider>
  );
}
