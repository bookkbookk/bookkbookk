import { CommentContent } from "@api/comments/type";

export type Bookmark = {
  bookmarkId: number;
  commentCount: number;
  page?: number;
} & CommentContent;
