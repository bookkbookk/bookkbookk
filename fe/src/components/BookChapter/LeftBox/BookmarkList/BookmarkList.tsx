import { Stack } from "@mui/material";
import { useBookmarkListState } from "context/BookmarkList/useBookmarkList";
import BookmarkThread from "./BookmarkThread";

export default function BookmarkList() {
  const bookmarkList = useBookmarkListState();

  return (
    <Stack width="100%">
      {bookmarkList.map((bookmark) => (
        <BookmarkThread key={bookmark.bookmarkId} {...{ bookmark }} />
      ))}
    </Stack>
  );
}
