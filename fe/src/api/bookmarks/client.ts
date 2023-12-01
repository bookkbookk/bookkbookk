import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Bookmark } from "./type";

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
}: {
  topicId: number;
  content: string;
  page?: number;
}) => {
  const { data } = await fetcher.post<Bookmark>(BOOK_API_PATH.bookmarks, {
    topicId,
    content,
    page,
  });

  return data;
};
