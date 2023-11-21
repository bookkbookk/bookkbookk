import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
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
  const queryClient = useQueryClient();
  const { mutate } = useMutation({
    mutationFn: patchTopic,
  });

  const onPatchTopicTitle = ({
    chapterId,
    topicId,
    title,
  }: {
    chapterId: number;
    topicId: number;
    title: string;
  }) => {
    mutate(
      { topicId, title },
      {
        onSuccess: () => {
          queryClient.invalidateQueries(queryKeys.topics.list({ chapterId }));
          onTitleChange?.(title);
        },
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
