import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postNewBook, putBookStatus } from "./client";
import { BookChapterStatusID, NewBookBody } from "./type";

export const useGetBookSearchResult = (searchWord: string) => {
  const { data: bookSearchResult } = useQuery({
    ...queryKeys.books.search(searchWord),
    enabled: !!searchWord,
  });

  return { bookSearchResult };
};

export const usePostNewBook = ({
  onSuccessCallback,
}: {
  onSuccessCallback: (bookId: number) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: postNewBook,
  });

  const onPostNewBook = (newBookBody: NewBookBody) => {
    mutate(newBookBody, {
      onSuccess: ({ createdBookId }) => {
        enqueueSnackbar(MESSAGE.NEW_BOOK_SUCCESS, {
          variant: "success",
        });
        onSuccessCallback(createdBookId);
      },
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_BOOK_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostNewBook };
};

export const usePutBookStatus = ({
  onSuccessCallback,
}: {
  onSuccessCallback: (statusId: BookChapterStatusID) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: putBookStatus,
  });

  const onPutBookStatus = ({
    bookId,
    statusId,
  }: {
    bookId: number;
    statusId: BookChapterStatusID;
  }) => {
    mutate(
      { bookId, statusId },
      {
        onSuccess: () => onSuccessCallback(statusId),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_BOOK_STATUS_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPutBookStatus };
};
