import { queryClient } from "@api/queryClient";
import { queryKeys } from "@api/queryKeys";
import { ACCESS_TOKEN_KEY, MESSAGE } from "@constant/index";
import { useMutation, useQuery } from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { patchMemberInfo } from "./client";
import { MemberInfo } from "./type";

export const loader = () => async () => {
  const accessToken = localStorage.getItem(ACCESS_TOKEN_KEY);
  // TODO: enabled 조건을 추가했는데 왜 accessToken 없을 떄도 쿼리가 실행되는지 확인해보기
  const query = queryKeys.members.info({ enabled: !!accessToken });

  return (
    queryClient.getQueryData(query.queryKey) ??
    (await queryClient.fetchQuery(query))
  );
};

export const useGetMember = (enabled?: boolean) =>
  useQuery(queryKeys.members.info({ enabled }));

export const usePatchMemberInfo = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const { mutate } = useMutation(patchMemberInfo);

  const onPatchMemberInfo = (memberInfo: MemberInfo) => {
    mutate(memberInfo, {
      onSuccess: () => {
        queryClient.invalidateQueries(queryKeys.members.info());
        onSuccessCallback();
      },
      onError: (error) => {
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
