import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { ChapterListItem, NewChapterBody } from "./type";

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

export const getChapters = async (bookId: number, statusId: number) => {
  const { data } = await fetcher.get<ChapterListItem[]>(
    `${BOOK_API_PATH.chapters}/${bookId}`,
    {
      params: { statusId },
    }
  );

  return data;
};

export const putChapterStatus = async ({
  chapterId,
  statusId,
}: {
  chapterId: number;
  statusId: number;
}) => {
  const { data } = await fetcher.put(BOOK_API_PATH.chapterStatus(chapterId), {
    statusId,
  });
  return data;
};
