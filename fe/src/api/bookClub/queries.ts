import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postNewBookClub } from "./client";
import { BookClubCreationInfo, BookClubStatus } from "./type";

export const usePostNewBookClub = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const { mutate } = useMutation({ mutationFn: postNewBookClub });

  const onPostNewBookClub = (bookClubInfo: BookClubCreationInfo) => {
    mutate(bookClubInfo, {
      onSuccess: onSuccessCallback,
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_BOOK_CLUB_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostNewBookClub };
};

export const useGetBookClubList = (option?: BookClubStatus) =>
  useQuery(queryKeys.bookClub.list(option));
