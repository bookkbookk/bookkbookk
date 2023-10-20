import { MESSAGE } from "@constant/index";
import { useMutation } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postNewBookClub } from "./client";
import { BookClubInfo } from "./type";

export const usePostNewBookClub = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const { mutate } = useMutation(postNewBookClub);

  const onPostNewBookClub = (bookClubInfo: BookClubInfo) => {
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
