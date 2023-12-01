import { useGetComments } from "@api/comments/queries";
import { Stack } from "@mui/material";
import { BookmarkComment } from "./BookmarkComment";

export function BookmarkCommentList({ bookmarkId }: { bookmarkId: number }) {
  const comments = useGetComments({ bookmarkId });

  return (
    <Stack gap={2} width="100%">
      {comments.map((comment) => (
        <BookmarkComment key={comment.commentId} {...{ comment }} />
      ))}
    </Stack>
  );
}
