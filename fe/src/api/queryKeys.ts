import { createQueryKeyStore } from "@lukemorales/query-key-factory";
import { postLogin } from "./auth/client";
import { OAuthLoginParams } from "./auth/type";
import { getBookSearchResult } from "./book/client";
import { getBookClubDetail, getBookClubList } from "./bookClub/client";
import { BookClubStatus } from "./bookClub/type";
import { getMember, getMemberBookList } from "./member/client";

export const queryKeys = createQueryKeyStore({
  members: {
    info: () => ({
      queryKey: ["getMember"],
      queryFn: getMember,
    }),
    books: ({ page, size }: { page: number; size: number }) => ({
      queryKey: ["getBookList", { page, size }],
      queryFn: () => getMemberBookList({ page, size }),
    }),
  },
  auth: {
    login: ({ provider, authCode }: OAuthLoginParams) => ({
      queryKey: ["postLogin"],
      queryFn: () => postLogin({ provider, authCode }),
    }),
  },
  books: {
    // TODO: 여기에 enabled 조건 추가하면 적용이 안되는 이유 찾아보기
    search: (searchWord: string) => ({
      queryKey: ["getBookSearchResult", searchWord],
      queryFn: () => getBookSearchResult(searchWord),
    }),
  },
  chapters: {
    list: ({ page, size }: { page: number; size: number }) => ({
      queryKey: ["getChapterList", { page, size }],
      // queryFn: () => getChapterList({ page, size }),
    }),
  },
  bookClub: {
    list: (option?: BookClubStatus) => ({
      queryKey: ["getBookClubList", option],
      queryFn: () => getBookClubList(option),
    }),
    join: (verificationCode?: string) => ({
      queryKey: ["postJoinBookClub", verificationCode],
    }),
    detail: (bookClubId: number) => ({
      queryKey: ["getBookClubDetail", bookClubId],
      queryFn: () => getBookClubDetail(bookClubId),
    }),
  },
});
