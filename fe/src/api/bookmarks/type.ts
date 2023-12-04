import { CommentContent } from "@api/comments/type";

export type Bookmark = {
  bookmarkId: number;
  commentCount: number;
  page?: number;
} & CommentContent;

export type NewBookmarkBody = {
  topicId: number;
  content: string;
  page?: number;
};

export type PatchBookmarkBody = {
  page: number;
  content: string;
};
