import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Comment } from "./type";

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
