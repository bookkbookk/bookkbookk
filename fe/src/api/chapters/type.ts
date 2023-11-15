import {
  BOOK_CHAPTERS_STATUS_LIST,
  BOOK_CHAPTERS_TAB,
} from "@components/constants";

export type ChapterListItem = {
  chapterId: number;
  statusId: BookChapterStatusID["id"];
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

export type BookChapterTabID = (typeof BOOK_CHAPTERS_TAB)[number];
export type BookChapterStatusID = (typeof BOOK_CHAPTERS_STATUS_LIST)[number];
