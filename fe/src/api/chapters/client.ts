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
    `${BOOK_API_PATH.books}/${bookId}/chapters`,
    {
      params: { statusId },
    }
  );

  return data;
};

export const patchChapter = async ({
  chapterId,
  statusId,
  title,
}: {
  chapterId: number;
  statusId?: number;
  title?: string;
}) => {
  const { data } = await fetcher.patch(
    `${BOOK_API_PATH.chapters}/${chapterId}`,
    {
      statusId,
      title,
    }
  );
  return data;
};
