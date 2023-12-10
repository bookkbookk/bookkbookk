import { Reaction } from "@api/comments/type";
import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Bookmark, NewBookmarkBody } from "./type";

export const getBookmarks = async (topicId: number) => {
  const { data } = await fetcher.get<Bookmark[]>(
    `${BOOK_API_PATH.topics}/${topicId}/bookmarks`
  );

  return data;
};

export const postBookmark = async ({
  topicId,
  content,
  page,
}: NewBookmarkBody) => {
  const { data } = await fetcher.post<{ newBookmark: Bookmark }>(
    BOOK_API_PATH.bookmarks,
    {
      topicId,
      content,
      page,
    }
  );

  return data;
};

export const patchBookmark = async ({
  bookmarkId,
  page,
  content,
}: {
  bookmarkId: number;
  page?: number;
  content?: string;
}) => {
  const { data } = await fetcher.patch(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}`,
    {
      page,
      content,
    }
  );
  return data;
};

export const deleteBookmark = async (bookmarkId: number) => {
  const { data } = await fetcher.delete(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}`
  );

  return data;
};

export const getReactions = async (bookmarkId: number) => {
  const { data } = await fetcher.get<Partial<Reaction>>(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}/reactions`
  );

  return data;
};

export const postReaction = async ({
  bookmarkId,
  reactionName,
}: {
  bookmarkId: number;
  reactionName: keyof Reaction;
}) => {
  const { data } = await fetcher.post(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}/reactions`,
    {
      reactionName,
    }
  );

  return data;
};

export const deleteReaction = async ({
  bookmarkId,
  reactionName,
}: {
  bookmarkId: number;
  reactionName: keyof Reaction;
}) => {
  const { data } = await fetcher.delete(
    `${BOOK_API_PATH.bookmarks}/${bookmarkId}/reactions/${reactionName}`
  );

  return data;
};
