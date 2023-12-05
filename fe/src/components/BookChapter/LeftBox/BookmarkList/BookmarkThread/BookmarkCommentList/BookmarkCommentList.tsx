import { Divider, Stack } from "@mui/material";
import { useCommentListState } from "context/CommentList/useCommentList";
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
  const comments = useCommentListState();
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
              <BookmarkComment key={comment.commentId} {...{ comment }} />
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
