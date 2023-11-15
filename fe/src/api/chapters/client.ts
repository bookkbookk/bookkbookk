import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { BookChapterTabID, ChapterListItem, NewChapterBody } from "./type";

export const postChapters = async ({ bookId, chapters }: NewChapterBody) => {
  const { data } = await fetcher.post<{ createdChapterIds: number[] }>(
    BOOK_API_PATH.chapters,
    {
      bookId,
      chapters,
    }
  );
  return data;
};

export const getChapters = async (
  bookId: number,
  statusId: BookChapterTabID["id"]
) => {
  const { data } = await fetcher.get<ChapterListItem[]>(
    BOOK_API_PATH.chapters,
    {
      params: {
        bookId,
        statusId,
      },
    }
  );

  return data;
};
