import { CommentTextarea } from "@components/common/CommentTextarea";

export default function BookmarkComment() {
  return (
    <CommentTextarea>
      <CommentTextarea.Content />
      <CommentTextarea.Footer />
    </CommentTextarea>
  );
}
