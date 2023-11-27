import { useSuspenseQuery } from "@tanstack/react-query";
import { queryKeys } from "./../queryKeys";
import { getBookmarks } from "./client";

export const useGetBookmarks = ({ topicId }: { topicId: number }) => {
  const { data: bookmarks } = useSuspenseQuery({
    ...queryKeys.bookmarks.list({ topicId }),
    queryFn: () => getBookmarks(topicId),
  });

  return bookmarks;
};
