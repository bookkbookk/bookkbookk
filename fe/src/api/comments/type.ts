export type CommentContent = {
  author: {
    memberId: number;
    nickname: string;
    profileImgUrl: string;
  };
  createdTime: string;
  content: string;
  reaction: {
    likeCount?: number;
  };
};

export type Comment = {
  commentId: number;
} & CommentContent;
