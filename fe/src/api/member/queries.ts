import { queryClient } from "@api/queryClient";
import { queryKeys } from "@api/queryKeys";
import { ACCESS_TOKEN_KEY } from "@constant/index";
import { useQuery } from "@tanstack/react-query";

export const loader = () => async () => {
  const accessToken = localStorage.getItem(ACCESS_TOKEN_KEY);
  // TODO: enabled 조건을 추가했는데 왜 accessToken 없을 떄도 쿼리가 실행되는지 확인해보기
  const query = queryKeys.members.info(!!accessToken);

  return (
    queryClient.getQueryData(query.queryKey) ??
    (await queryClient.fetchQuery(query))
  );
};

export const useGetMember = (enabled?: boolean) =>
  useQuery(queryKeys.members.info(enabled));
