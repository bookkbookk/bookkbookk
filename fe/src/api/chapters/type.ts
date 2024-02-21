import {
  BOOK_CHAPTERS_STATUS_LIST,
  BOOK_CHAPTERS_TAB,
  BOOK_CLUB_TAB,
} from "@components/constants";

export type ChapterListItem = {
  chapterId: number;
  statusId: BookChapterStatus["id"];
  title: string;
  topics: TopicItemInfo[];
};

type BookmarkInfo = {
  authorProfileImgUrl: string;
  content: string;
};

export type TopicItemInfo = {
  topicId: number;
  title: string;
  recentBookmark: BookmarkInfo;
};

export type NewChapterBody = {
  bookId: number;
  chapters: Chapter[];
};

export type Chapter = {
  title: string;
  topics?: { title: string }[];
};

export type BookChapterTab = (typeof BOOK_CHAPTERS_TAB)[number];
export type BookChapterStatus = (typeof BOOK_CHAPTERS_STATUS_LIST)[number];
export type BookClubStatus = (typeof BOOK_CLUB_TAB)[number];
