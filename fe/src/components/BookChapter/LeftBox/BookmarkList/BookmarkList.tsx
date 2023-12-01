import { useGetBookmarks } from "@api/bookmarks/queries";
import { Stack } from "@mui/material";
import { BookmarkThread } from "./BookmarkThread";

export default function BookmarkList({ topicId }: { topicId: number }) {
  const bookmarks = useGetBookmarks({ topicId });

  return (
    <Stack width="100%">
      {bookmarks.map((bookmark) => (
        <BookmarkThread key={bookmark.bookmarkId} {...{ bookmark }} />
      ))}
    </Stack>
  );
}
