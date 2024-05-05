import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Comment, Reaction } from "./type";

export const getComments = async (bookmarkId: number) => {
  const { data } = await fetcher.get<Comment[]>(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}/comments`
  );

  return data;
};

export const postComment = async ({
  bookmarkId,
  content,
}: {
  bookmarkId: number;
  content: string;
}) => {
  const { data } = await fetcher.post(BOOK_API_PATH.comments, {
    bookmarkId,
    content,
  });

  return data;
};

export const patchComment = async ({
  commentId,
  content,
}: {
  commentId: number;
  content: string;
}) => {
  const { data } = await fetcher.patch(
    `${BOOK_API_PATH.comments}/${commentId}`,
    {
      content,
    }
  );

  return data;
};

export const deleteComment = async (commentId: number) => {
  const { data } = await fetcher.delete(
    `${BOOK_API_PATH.comments}/${commentId}`
  );

  return data;
};

export const getReactions = async (commentId: number) => {
  const { data } = await fetcher.get<Partial<Reaction>>(
    `${BOOK_API_PATH.comments}/${commentId}/reactions`
  );

  return data;
};

export const postReaction = async ({
  commentId,
  reactionName,
}: {
  commentId: number;
  reactionName: keyof Reaction;
}) => {
  const { data } = await fetcher.post(
    `${BOOK_API_PATH.comments}/${commentId}/reactions`,
    {
      reactionName,
    }
  );

  return data;
};

export const deleteReaction = async ({
  commentId,
  reactionName,
}: {
  commentId: number;
  reactionName: keyof Reaction;
}) => {
  const { data } = await fetcher.delete(
    `${BOOK_API_PATH.comments}/${commentId}/reactions/${reactionName}`
  );

  return data;
};
