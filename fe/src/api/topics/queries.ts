import { queryKeys } from "@api/queryKeys";
import { useSuspenseQuery } from "@tanstack/react-query";
import { getTopics } from "./client";

export const useGetTopics = ({ chapterId }: { chapterId: number }) => {
  const { data: chapters } = useSuspenseQuery({
    ...queryKeys.topics.list({ chapterId }),
    queryFn: () => getTopics(chapterId),
  });

  return chapters;
};
