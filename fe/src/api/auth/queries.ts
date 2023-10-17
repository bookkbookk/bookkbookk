import { useQuery } from "@tanstack/react-query";
import { queryKeys } from "../queryKeys";
import { OAuthLoginParams } from "./type";

export const useOAuthLogin = ({ provider, OAuthCode }: OAuthLoginParams) =>
  useQuery(queryKeys.auth.login({ provider, OAuthCode }));
