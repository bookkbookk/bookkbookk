import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postNewBook } from "./client";
import { NewBookBody } from "./type";

export const useGetBookSearchResult = (searchWord: string) =>
  useQuery(queryKeys.book.search(searchWord));

export const usePostNewBook = ({
  onSuccessCallback,
}: {
  onSuccessCallback: (bookId: number) => void;
}) => {
  const { mutate } = useMutation(postNewBook);

  const onPostNewBookClub = (newBookBody: NewBookBody) => {
    mutate(newBookBody, {
      onSuccess: ({ createdBookId }) => onSuccessCallback(createdBookId),
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_BOOK_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostNewBookClub };
};
