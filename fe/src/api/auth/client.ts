import { fetcher } from "@api/fetcher";
import { AUTH_API_PATH } from "./constants";
import { OAuthLoginParams, Tokens } from "./type";

export const postRefreshToken = async (refreshToken: string) => {
  return await fetcher.post<{ accessToken: string }>(AUTH_API_PATH.refresh, {
    refreshToken,
  });
};

export const postLogin = async ({ provider, OAuthCode }: OAuthLoginParams) => {
  return await fetcher.post<Tokens & { isNewMember: boolean }>(
    `${AUTH_API_PATH.login}/${provider}`,
    {
      OAuthCode,
    }
  );
};
