import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { Comment } from "@components/common/Comment";
import { Divider, Stack } from "@mui/material";
import { useState } from "react";
import BookmarkComment from "../BookmarkComment";
import { BookmarkCommentList } from "./BookmarkCommentList";

export function Bookmark({ bookmark }: { bookmark: BookmarkProps }) {
  const { bookmarkId, author, createdTime, content } = bookmark;

  const [isReplying, setIsReplying] = useState(false);
  const toggleReplying = () => setIsReplying((prev) => !prev);

  return (
    <Stack>
      <Comment>
        <Comment.Header {...{ author, createdTime }} />
        <Comment.Content {...{ content }} />
        <Comment.Footer onReplyButtonClick={toggleReplying} />
      </Comment>
      <Stack
        paddingLeft={10}
        gap={3}
        display="flex"
        flexDirection={"row"}
        paddingY={1}>
        <Divider orientation="vertical" />
        <Stack width="100%" paddingY={2} gap={3}>
          <BookmarkCommentList bookmarkId={bookmarkId} />
          {isReplying && <BookmarkComment bookmarkId={bookmarkId} />}
        </Stack>
      </Stack>
    </Stack>
  );
}
