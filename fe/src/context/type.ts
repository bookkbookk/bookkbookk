import { Bookmark } from "@api/bookmarks/type";
import { Comment } from "@api/comments/type";

export type BookmarkListState = Bookmark[];

export type BookmarkListAction = {
  addBookmark: ({ newBookmark }: { newBookmark: Bookmark }) => void;
  updateContent: ({
    bookmarkId,
    newContent,
  }: {
    bookmarkId: number;
    newContent: string;
  }) => void;
  updatePage: ({
    bookmarkId,
    newPage,
  }: {
    bookmarkId: number;
    newPage: number;
  }) => void;
  deleteBookmark: ({ bookmarkId }: { bookmarkId: number }) => void;
};

export type CommentListState = Comment[];

export type CommentListAction = {
  updateContent: ({
    commentId,
    newContent,
  }: {
    commentId: number;
    newContent: string;
  }) => void;
  deleteComment: ({ commentId }: { commentId: number }) => void;
};
