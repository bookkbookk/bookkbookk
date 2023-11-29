export type NewBookmarkState = {
  page?: string;
  content: string;
};

export type NewBookmarkAction = {
  setPage: (page: string) => void;
  setContent: (content: string) => void;
};

export type BookmarkCommentState = {
  content: string;
};

export type BookmarkCommentAction = {
  setContent: (content: string) => void;
};
