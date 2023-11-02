import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { Button } from "@mui/material";
import { useMutation, useQuery } from "@tanstack/react-query";
import { closeSnackbar, enqueueSnackbar } from "notistack";
import { postJoinBookClub, postNewBookClub, sendEmail } from "./client";
import {
  BookClubCreationInfo,
  BookClubStatus,
  EmailSubmitInfo,
  NewBookClubInfo,
} from "./type";

export const usePostNewBookClub = ({
  onSuccessCallback,
}: {
  onSuccessCallback: (newBookClubInfo: NewBookClubInfo) => void;
}) => {
  const { mutate } = useMutation({ mutationFn: postNewBookClub });

  const onPostNewBookClub = (bookClubInfo: BookClubCreationInfo) => {
    mutate(bookClubInfo, {
      onSuccess: (newBookClubInfo) => onSuccessCallback(newBookClubInfo),
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

export const useSendEmail = () => {
  const { mutate } = useMutation({ mutationFn: sendEmail });

  const onSendEmail = (emailSubmitInfo: EmailSubmitInfo) => {
    mutate(emailSubmitInfo, {
      onSuccess: () => enqueueSnackbar(MESSAGE.SEND_EMAIL_SUCCESS),
      onError: () => {
        enqueueSnackbar(MESSAGE.SEND_EMAIL_ERROR, {
          variant: "error",
          autoHideDuration: 10000,
          action: (key) => (
            <>
              <Button
                onClick={() => onSendEmail(emailSubmitInfo)}
                color="inherit">
                재시도
              </Button>
              <Button onClick={() => closeSnackbar(key)} color="inherit">
                닫기
              </Button>
            </>
          ),
        });
      },
    });
  };

  return { onSendEmail };
};

export const useAuthBookClub = ({
  bookClubCode,
  isLogin,
}: {
  bookClubCode: string;
  isLogin: boolean;
}) => {
  const {
    data: bookClubInfo,
    isLoading,
    isError,
    isSuccess,
  } = useQuery({
    ...queryKeys.bookClub.join(bookClubCode),
    queryFn: () => postJoinBookClub(bookClubCode),
    enabled: !!bookClubCode && !!isLogin,
  });

  return { bookClubInfo, isLoading, isError, isSuccess };
};
