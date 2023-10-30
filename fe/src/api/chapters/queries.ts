import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { postChapters } from "./client";
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
          queryKeys.chapters.list({ page: 1, size: 10 })
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

// TODO: 챕터 상태 필터링 조건 추가 필요
// export const useGetChapters = ({
//   page,
//   size,
// }: {
//   page: number;
//   size: number;
// }) => {
//   const {
//     data: chapters,
//     isLoading,
//     isSuccess,
//     isError,
//   } = useQuery(queryKeys.chapters.list({ page, size }));

//   return { chapters, isLoading, isSuccess, isError };
// };
