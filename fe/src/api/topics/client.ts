import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Topic } from "./type";

export const getTopics = async (chapterId: number) => {
  const { data } = await fetcher.get<Topic[]>(
    `${BOOK_API_PATH.topics}/${chapterId}`
  );

  return data;
};
