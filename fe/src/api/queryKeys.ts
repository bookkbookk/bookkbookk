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
    search: (searchWord: string) => ({
      queryKey: ["getBookSearchResult", searchWord],
      queryFn: () => getBookSearchResult(searchWord),
    }),
  },

  chapters: {
    list: ({ bookId }: { bookId: number }) => ({
      queryKey: ["getChapters", { bookId }],
    }),
  },
  topics: {
    list: ({ chapterId }: { chapterId: number }) => ({
      queryKey: ["getTopics", { chapterId }],
    }),
  },

  bookmarks: {
    list: ({ topicId }: { topicId: number }) => ({
      queryKey: ["getBookmarks", { topicId }],
    }),
    reactions: ({ bookmarkId }: { bookmarkId: number }) => ({
      queryKey: ["getReactions", { bookmarkId }],
    }),
  },

  comments: {
    list: ({ bookmarkId }: { bookmarkId: number }) => ({
      queryKey: ["getComments", { bookmarkId }],
    }),
    reactions: ({ commentId }: { commentId: number }) => ({
      queryKey: ["getReactions", { commentId }],
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
    books: ({ bookClubId }: { bookClubId: number }) => ({
      queryKey: ["getBookList", bookClubId],
    }),
  },
});
