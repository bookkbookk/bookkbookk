export type Reaction = {
  like: string[];
  love: string[];
  clap: string[];
  congratulation: string[];
  rocket: string[];
};

export type CommentContent = {
  author: {
    memberId: number;
    nickname: string;
    profileImgUrl: string;
  };
  createdTime: string;
  content: string;
  reaction: Partial<Reaction>;
};

export type Comment = {
  commentId: number;
} & CommentContent;

export type NewCommentBody = {
  bookmarkId: number;
  content: string;
};
