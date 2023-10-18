import { useQuery } from "@tanstack/react-query";
import { queryKeys } from "../queryKeys";
import { OAuthLoginParams } from "./type";

export const useOAuthLogin = ({ provider, authCode }: OAuthLoginParams) =>
  useQuery(queryKeys.auth.login({ provider, authCode }));
