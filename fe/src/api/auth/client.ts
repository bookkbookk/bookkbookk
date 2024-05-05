import { fetcher } from "@api/fetcher";
import { AUTH_API_PATH } from "../constants";
import { OAuthLoginParams, Tokens } from "./type";

export const postRefreshToken = async () => {
  return await fetcher.post<{ accessToken: string }>(
    AUTH_API_PATH.reissueToken
  );
};

export const postLogin = async ({ provider, authCode }: OAuthLoginParams) => {
  return await fetcher.post<Tokens & { isNewMember: boolean }>(
    `${AUTH_API_PATH.login}/${provider}`,
    {
      authCode,
    }
  );
};

export const postLogout = async () => {
  return await fetcher.post(AUTH_API_PATH.logout);
};
