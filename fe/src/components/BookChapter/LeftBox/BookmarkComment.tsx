import { CommentTextarea } from "@components/common/CommentTextarea";
import { BookmarkCommentProvider } from "context/BookmarkComment/BookmarkCommentProvider";

export default function BookmarkComment({
  bookmarkId,
}: {
  bookmarkId: number;
}) {
  return (
    <BookmarkCommentProvider bookmarkId={bookmarkId}>
      <CommentTextarea>
        <CommentTextarea.Comment />
      </CommentTextarea>
    </BookmarkCommentProvider>
  );
}
