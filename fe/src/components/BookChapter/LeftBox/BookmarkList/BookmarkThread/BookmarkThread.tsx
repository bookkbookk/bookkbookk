import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { useGetComments } from "@api/comments/queries";
import { Stack } from "@mui/material";
import { CommentListProvider } from "context/CommentList/CommentListProvider";
import { useState } from "react";
import Bookmark from "./Bookmark";
import { BookmarkCommentList } from "./BookmarkCommentList/BookmarkCommentList";

export function BookmarkThread({ bookmark }: { bookmark: BookmarkProps }) {
  const { bookmarkId } = bookmark;
  const commentList = useGetComments({ bookmarkId });

  const [isReplying, setIsReplying] = useState(false);
  const toggleReplying = () => setIsReplying((prev) => !prev);

  return (
    <Stack width="100%">
      <Bookmark {...{ bookmark, toggleReplying }} />
      <Stack width="100%" paddingY={2} gap={3}>
        <CommentListProvider commentList={commentList}>
          <BookmarkCommentList
            bookmarkId={bookmarkId}
            isReplying={isReplying}
            toggleReplying={toggleReplying}
          />
        </CommentListProvider>
      </Stack>
    </Stack>
  );
}
