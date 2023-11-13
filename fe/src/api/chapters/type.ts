import { BOOK_CHAPTERS_TAB } from "@components/constants";

export type ChapterListItem = {
  chapterId: number;
  statusId: BookChapterStatus["id"];
  title: string;
  recentBookmark: RecentBookmark;
  topics: {
    topicId: number;
    title: string;
    recentBookmark: RecentBookmark;
  }[];
};

type RecentBookmark = {
  authorProfileImgUrl: string;
  content: string;
};

export type NewChapterBody = {
  bookId: number;
  chapters: Chapter[];
};

export type Chapter = {
  title: string;
  topics?: { title: string }[];
};

export type BookChapterStatus = (typeof BOOK_CHAPTERS_TAB)[number];
