import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useSuspenseQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { getTopics, patchTopic } from "./client";

export const useGetTopics = ({ chapterId }: { chapterId: number }) => {
  const { data: chapters } = useSuspenseQuery({
    ...queryKeys.topics.list({ chapterId }),
    queryFn: () => getTopics(chapterId),
  });

  return chapters;
};

export const usePatchTopic = ({
  onTitleChange,
}: {
  onTitleChange: (newTitle: string) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: patchTopic,
  });

  const onPatchTopicTitle = ({
    topicId,
    title,
  }: {
    topicId: number;
    title: string;
  }) => {
    mutate(
      { topicId, title },
      {
        onSuccess: () => onTitleChange?.(title),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_TOPIC_TITLE_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPatchTopicTitle };
};
