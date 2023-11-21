import { BookChapterStatusID } from "@api/book/type";
import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { getChapters, postChapters, putChapterStatus } from "./client";
import { NewChapterBody } from "./type";

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
  statusId: number;
}) => {
  const { data: chapters } = useSuspenseQuery({
    ...queryKeys.chapters.list({ bookId }),
    queryFn: () => getChapters(bookId, statusId),
  });

  return chapters;
};

export const usePutChapterStatus = ({
  onSuccessCallback,
}: {
  onSuccessCallback: (statusId: BookChapterStatusID) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: putChapterStatus,
  });

  const onPutChapterStatus = ({
    chapterId,
    statusId,
  }: {
    chapterId: number;
    statusId: BookChapterStatusID;
  }) => {
    mutate(
      { chapterId, statusId },
      {
        onSuccess: () => onSuccessCallback(statusId),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_CHAPTER_STATUS_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPutChapterStatus };
};
