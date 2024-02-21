import { BookChapterStatusID } from "@api/book/type";
import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { getChapters, patchChapter, postChapters } from "./client";
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

export const usePatchChapter = ({
  onStatusChange,
  onTitleChange,
}: {
  onStatusChange?: (statusId: BookChapterStatusID) => void;
  onTitleChange?: (newTitle: string) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: patchChapter,
  });

  const onPatchChapterStatus = ({
    chapterId,
    statusId,
  }: {
    chapterId: number;
    statusId: BookChapterStatusID;
  }) => {
    mutate(
      { chapterId, statusId },
      {
        onSuccess: () => onStatusChange?.(statusId),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_CHAPTER_STATUS_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  const onPatchChapterTitle = ({
    chapterId,
    title,
  }: {
    chapterId: number;
    title: string;
  }) => {
    mutate(
      { chapterId, title },
      {
        onSuccess: () => onTitleChange?.(title),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_CHAPTER_TITLE_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPatchChapterStatus, onPatchChapterTitle };
};
