import { MESSAGE } from "@constant/index";
import { Button } from "@mui/material";
import { useMutation } from "@tanstack/react-query";
import { closeSnackbar, enqueueSnackbar } from "notistack";
import { postNewGathering } from "./client";
import { NewBookClubGathering } from "./type";

export const usePostNewGathering = ({
  callback,
}: {
  callback: (bookId: number) => void;
}) => {
  const { mutate } = useMutation({ mutationFn: postNewGathering });

  const onPostNewGathering = (gatheringInfo: {
    bookClubId: number;
    gatheringInfo: NewBookClubGathering;
  }) => {
    mutate(gatheringInfo, {
      // TODO: onSuccess invalidateQuery 북클럽 모임 목록 조회
      onSuccess: () => callback(gatheringInfo.gatheringInfo.bookId),
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_GATHERING_ERROR, {
          variant: "error",
          autoHideDuration: 5000,
          action: (key) => (
            <>
              <Button
                onClick={() => onPostNewGathering(gatheringInfo)}
                color="inherit">
                재시도
              </Button>
              <Button
                onClick={() => {
                  closeSnackbar(key);
                  callback(gatheringInfo.gatheringInfo.bookId);
                }}
                color="inherit">
                챕터 추가하러 가기
              </Button>
            </>
          ),
        });
      },
    });
  };

  return { onPostNewGathering };
};
