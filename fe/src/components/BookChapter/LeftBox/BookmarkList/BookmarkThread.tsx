import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { Divider, Stack } from "@mui/material";
import { useState } from "react";
import NewBookmarkComment from "../NewBookmarkComment";
import Bookmark from "./Bookmark";
import { BookmarkCommentList } from "./BookmarkCommentList";

export function BookmarkThread({ bookmark }: { bookmark: BookmarkProps }) {
  const { bookmarkId } = bookmark;

  const [isReplying, setIsReplying] = useState(false);
  const toggleReplying = () => setIsReplying((prev) => !prev);

  return (
    <Stack width="100%">
      <Bookmark {...{ bookmark, toggleReplying }} />
      <Stack
        paddingLeft={10}
        gap={3}
        display="flex"
        flexDirection={"row"}
        paddingY={1}>
        <Divider orientation="vertical" />
        <Stack width="100%" paddingY={2} gap={3}>
          <BookmarkCommentList bookmarkId={bookmarkId} />
          {isReplying && (
            <NewBookmarkComment {...{ bookmarkId, toggleReplying }} />
          )}
        </Stack>
      </Stack>
    </Stack>
  );
}
