import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Bookmark, NewBookmarkBody } from "./type";

export const getBookmarks = async (topicId: number) => {
  const { data } = await fetcher.get<Bookmark[]>(
    `${BOOK_API_PATH.topics}/${topicId}/bookmarks`
  );

  return data;
};

export const postBookmark = async ({
  topicId,
  content,
  page,
}: NewBookmarkBody) => {
  const { data } = await fetcher.post(BOOK_API_PATH.bookmarks, {
    topicId,
    content,
    page,
  });

  return data;
};
