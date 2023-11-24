import { BOOK_API_PATH } from "@api/constants";
import { fetcher } from "@api/fetcher";
import { Topic } from "./type";

export const getTopics = async (chapterId: number) => {
  const { data } = await fetcher.get<Topic[]>(
    `${BOOK_API_PATH.chapters}/${chapterId}/topics`
  );

  return data;
};

export const patchTopic = async ({
  topicId,
  title,
}: {
  topicId: number;
  title: string;
}) => {
  const { data } = await fetcher.patch(`${BOOK_API_PATH.topics}/${topicId}`, {
    title,
  });

  return data;
};
