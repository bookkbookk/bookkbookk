import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { getChapters, postChapters } from "./client";
import { BookChapterTabID, NewChapterBody } from "./type";

export const usePostNewChapters = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: postChapters,
  });

  const onPostChapters = (newChapterBody: NewChapterBody) => {
    mutate(newChapterBody, {
      onSuccess: () => {
        queryClient.invalidateQueries(
          queryKeys.chapters.list({
            bookId: newChapterBody.bookId,
          })
        );
        onSuccessCallback();
      },
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_CHAPTER_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostChapters };
};

export const useGetChapters = ({
  bookId,
  statusId,
}: {
  bookId: number;
  statusId: BookChapterTabID["id"];
}) => {
  const { data: chapters } = useSuspenseQuery({
    ...queryKeys.chapters.list({ bookId }),
    queryFn: () => getChapters(bookId, statusId),
  });

  return chapters;
};
