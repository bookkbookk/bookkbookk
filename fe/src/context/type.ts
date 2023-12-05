import { Bookmark } from "@api/bookmarks/type";
import { Comment } from "@api/comments/type";

export type NewBookmarkState = {
  page?: string;
  content: string;
};

export type NewBookmarkAction = {
  setPage: (page: string) => void;
  setContent: (content: string) => void;
};

export type NewCommentState = {
  content: string;
};

export type NewCommentAction = {
  setContent: (content: string) => void;
};

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
