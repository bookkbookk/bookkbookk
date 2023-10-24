import { ALADIN_API_PATH, BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { stringify } from "qs";
import { BookInfo, NewBookBody } from "./type";

export const getBookSearchResult = async (searchWord: string) => {
  const requestUrl = `${ALADIN_API_PATH.search}?${stringify({
    search: searchWord,
  })}`;
  const { data } = await fetcher.get<BookInfo[]>(requestUrl);

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
