export type BookmarkState = {
  page?: string;
  content: string;
};

export type BookmarkAction = {
  setPage: (page: string) => void;
  setContent: (content: string) => void;
};

export type BookmarkCommentState = {
  content: string;
};

export type BookmarkCommentAction = {
  setContent: (content: string) => void;
};
