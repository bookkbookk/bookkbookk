import { useGetBookmarks } from "@api/bookmarks/queries";
import { Stack } from "@mui/material";
import { Bookmark } from "./Bookmark";

export default function BookmarkList({ topicId }: { topicId: number }) {
  const bookmarks = useGetBookmarks({ topicId });

  return (
    <Stack>
      {bookmarks.map((bookmark) => (
        <Bookmark key={bookmark.bookmarkId} {...{ bookmark }} />
      ))}
    </Stack>
  );
}
