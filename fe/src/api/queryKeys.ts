import { createQueryKeyStore } from "@lukemorales/query-key-factory";
import { postLogin } from "./auth/client";
import { OAuthLoginParams } from "./auth/type";
import { getBookList, getBookSearchResult } from "./book/client";
import { getBookClubList } from "./bookClub/client";
import { BookClubStatus } from "./bookClub/type";
import { getMember } from "./member/client";

export const queryKeys = createQueryKeyStore({
  members: {
    info: (option?: { enabled?: boolean }) => ({
      queryKey: ["getMember"],
      queryFn: getMember,
      enabled: option?.enabled,
    }),
  },
  auth: {
    login: ({ provider, authCode }: OAuthLoginParams) => ({
      queryKey: ["postLogin"],
      queryFn: () => postLogin({ provider, authCode }),
      enabled: !!authCode && !!provider,
    }),
  },
  books: {
    search: (searchWord: string) => ({
      queryKey: ["getBookSearchResult", searchWord],
      queryFn: () => getBookSearchResult(searchWord),
      enabled: !!searchWord,
    }),
    list: ({ page, size }: { page: number; size: number }) => ({
      queryKey: ["getBookList", { page, size }],
      queryFn: () => getBookList({ page, size }),
    }),
  },
  bookClub: {
    list: (option?: BookClubStatus) => ({
      queryKey: ["getBookClubList", option],
      queryFn: () => getBookClubList(option),
    }),
  },
});
