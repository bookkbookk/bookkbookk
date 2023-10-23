import { ALADIN_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { stringify } from "qs";
import { BookInfo } from "./type";

export const getBookSearchResult = async (searchWord: string) => {
  const requestUrl = `${ALADIN_API_PATH.search}?${stringify({
    search: searchWord,
  })}`;
  const { data } = await fetcher.get<BookInfo[]>(requestUrl);

  return data;
};
