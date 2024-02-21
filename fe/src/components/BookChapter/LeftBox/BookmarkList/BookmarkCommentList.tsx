import { useGetComments } from "@api/comments/queries";
import { Comment } from "@components/common/Comment";
import { Stack } from "@mui/material";

export function BookmarkCommentList({ bookmarkId }: { bookmarkId: number }) {
  const comments = useGetComments({ bookmarkId });

  return (
    <Stack gap={2} width="100%">
      {comments.map((comment) => {
        const { commentId, author, createdTime, content } = comment;

        return (
          <Comment key={commentId}>
            <Comment.Header {...{ author, createdTime }} />
            <Comment.Content {...{ content }} />
          </Comment>
        );
      })}
    </Stack>
  );
}
