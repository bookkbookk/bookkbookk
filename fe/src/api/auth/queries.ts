import { useMutation, useQuery } from "@tanstack/react-query";
import { queryKeys } from "../queryKeys";
import { postLogout } from "./client";
import { OAuthLoginParams } from "./type";
import { logout } from "./utils";

export const useOAuthLogin = ({ provider, authCode }: OAuthLoginParams) =>
  useQuery(queryKeys.auth.login({ provider, authCode }));

export const useLogout = () => {
  const { mutate } = useMutation({ mutationFn: postLogout, onSuccess: logout });

  return { onLogout: mutate };
};
