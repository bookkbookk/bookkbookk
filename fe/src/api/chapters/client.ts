import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { NewChapterBody } from "./type";

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
