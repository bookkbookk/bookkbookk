import { getAccessToken } from "@api/fetcher";
import { queryClient } from "@api/queryClient";
import { queryKeys } from "@api/queryKeys";
import { MESSAGE } from "@constant/index";
import { useMutation, useQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { patchMemberInfo } from "./client";
import { MemberInfo } from "./type";

export const loader = () => async () => {
  const accessToken = getAccessToken();

  if (!accessToken) {
    return null;
  }

  const query = queryKeys.members.info();

  return (
    queryClient.getQueryData({ ...query.queryKey, enabled: !!accessToken }) ??
    (await queryClient.fetchQuery(query))
  );
};

export const useGetMember = (enabled?: boolean) =>
  useQuery({ ...queryKeys.members.info(), enabled });

export const usePatchMemberInfo = ({
  onSuccessCallback,
}: {
  onSuccessCallback: ({
    newNickname,
    newProfileImgUrl,
  }: {
    newNickname: string;
    newProfileImgUrl: string;
  }) => void;
}) => {
  const { mutate } = useMutation({ mutationFn: patchMemberInfo });

  const onPatchMemberInfo = (memberInfo: MemberInfo) => {
    mutate(memberInfo, {
      onSuccess: (newMemberInfo) => {
        onSuccessCallback(newMemberInfo);
      },
      // TODO: error type 정의
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      onError: (error: any) => {
        if (error.response.status === 409) {
          return enqueueSnackbar(MESSAGE.NICKNAME_DUPLICATED, {
            variant: "error",
          });
        }
        enqueueSnackbar(MESSAGE.MEMBER_UPDATE_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPatchMemberInfo };
};

export const useGetBooks = ({ page, size }: { page: number; size: number }) => {
  const {
    data: books,
    isLoading,
    isSuccess,
    isError,
  } = useQuery(queryKeys.members.books({ page, size }));

  return { books, isLoading, isSuccess, isError };
};
