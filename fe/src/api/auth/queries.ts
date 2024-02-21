import { setAccessToken } from "@api/fetcher";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useSetIsLogin, useSetMember } from "store/useMember";
import { queryKeys } from "../queryKeys";
import { postLogout } from "./client";
import { OAuthLoginParams } from "./type";

export const useOAuthLogin = ({ provider, authCode }: OAuthLoginParams) =>
  useQuery(queryKeys.auth.login({ provider, authCode }));

export const useLogout = () => {
  const setMember = useSetMember();
  const setIsLogin = useSetIsLogin();

  const logout = () => {
    setIsLogin(false);
    setMember({ type: "RESET" });
    setAccessToken("");
  };

  const { mutate } = useMutation({ mutationFn: postLogout, onSuccess: logout });

  return { onLogout: mutate };
};
