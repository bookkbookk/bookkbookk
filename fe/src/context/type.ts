import { Bookmark } from "@api/bookmarks/type";
import { Comment } from "@api/comments/type";

export type BookmarkListState = Bookmark[];

export type BookmarkListAction = {
  setContent: ({
    bookmarkId,
    newContent,
  }: {
    bookmarkId: number;
    newContent: string;
  }) => void;
  setPage: ({
    bookmarkId,
    newPage,
  }: {
    bookmarkId: number;
    newPage: number;
  }) => void;
};

export type CommentListState = Comment[];

export type CommentListAction = {
  setContent: ({
    commentId,
    newContent,
  }: {
    commentId: number;
    newContent: string;
  }) => void;
};
