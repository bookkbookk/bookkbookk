import { useSuspenseQuery } from "@tanstack/react-query";
import { queryKeys } from "./../queryKeys";
import { getComments } from "./client";

export const useGetComments = ({ bookmarkId }: { bookmarkId: number }) => {
  const { data: bookmarks } = useSuspenseQuery({
    ...queryKeys.comments.list({ bookmarkId }),
    queryFn: () => getComments(bookmarkId),
  });

  return bookmarks;
};
