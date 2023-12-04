import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { Stack } from "@mui/material";
import { useState } from "react";
import Bookmark from "./Bookmark";
import { BookmarkCommentList } from "./BookmarkCommentList/BookmarkCommentList";

export function BookmarkThread({ bookmark }: { bookmark: BookmarkProps }) {
  const { bookmarkId } = bookmark;

  const [isReplying, setIsReplying] = useState(false);
  const toggleReplying = () => setIsReplying((prev) => !prev);

  return (
    <Stack width="100%">
      <Bookmark {...{ bookmark, toggleReplying }} />
      <Stack width="100%" paddingY={2} gap={3}>
        <BookmarkCommentList
          bookmarkId={bookmarkId}
          isReplying={isReplying}
          toggleReplying={toggleReplying}
        />
      </Stack>
    </Stack>
  );
}
