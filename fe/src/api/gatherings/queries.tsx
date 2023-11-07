import { MESSAGE } from "@constant/index";
import { useMutation } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postNewGathering } from "./client";
import { NewBookClubGathering } from "./type";

export const usePostNewGathering = ({
  onPostNewGatheringSuccess,
}: {
  onPostNewGatheringSuccess: (bookId: number) => void;
}) => {
  const { mutate } = useMutation({ mutationFn: postNewGathering });

  const onPostNewGathering = (gatheringInfo: {
    bookClubId: number;
    gatheringInfo: NewBookClubGathering;
  }) => {
    mutate(gatheringInfo, {
      // TODO: onSuccess invalidateQuery 북클럽 모임 목록 조회
      onSuccess: () =>
        onPostNewGatheringSuccess(gatheringInfo.gatheringInfo.bookId),
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_GATHERING_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostNewGathering };
};
