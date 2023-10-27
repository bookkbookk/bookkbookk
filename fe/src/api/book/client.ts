import { ALADIN_API_PATH, BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { BookInfo, BookList, NewBookBody, NewChapterBody } from "./type";

export const getBookSearchResult = async (searchWord: string) => {
  const { data } = await fetcher.get<BookInfo[]>(ALADIN_API_PATH.search, {
    params: {
      search: searchWord,
    },
  });

  return data;
};

export const postNewBook = async (newBookBody: NewBookBody) => {
  const { data } = await fetcher.post<{ createdBookId: number }>(
    BOOK_API_PATH.books,
    {
      ...newBookBody,
    }
  );
  return data;
};

export const getBookList = async ({
  page,
  size,
}: {
  page: number;
  size: number;
}) => {
  const { data } = await fetcher.get<BookList>(BOOK_API_PATH.books, {
    params: {
      page,
      size,
    },
  });
  return data;
};

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
