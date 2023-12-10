import { useGetComments } from "@api/comments/queries";
import { Divider, Stack } from "@mui/material";
import NewBookmarkComment from "../NewBookmarkComment";
import { BookmarkComment } from "./BookmarkComment";

export function BookmarkCommentList({
  bookmarkId,
  isReplying,
  toggleReplying,
}: {
  bookmarkId: number;
  isReplying: boolean;
  toggleReplying: () => void;
}) {
  const comments = useGetComments({ bookmarkId });
  const hasComments = !!comments.length;

  return (
    <Stack
      paddingLeft={10}
      gap={3}
      display="flex"
      flexDirection={"row"}
      paddingY={1}>
      {(hasComments || isReplying) && <Divider orientation="vertical" />}
      <Stack width="100%">
        {hasComments && (
          <Stack gap={2} width="100%" marginBottom={2}>
            {comments.map((comment) => (
              <BookmarkComment
                key={comment.content}
                bookmarkId={bookmarkId}
                {...{ comment }}
              />
            ))}
          </Stack>
        )}
        {isReplying && (
          <NewBookmarkComment {...{ bookmarkId, toggleReplying }} />
        )}
      </Stack>
    </Stack>
  );
}
