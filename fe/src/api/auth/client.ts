import { fetcher } from "@api/fetcher";
import { REFRESH_TOKEN_KEY } from "@constant/index";
import { AUTH_API_PATH } from "./constants";
import { OAuthLoginParams, Tokens } from "./type";

export const postRefreshToken = async (refreshToken: string) => {
  return await fetcher.post<{ accessToken: string }>(AUTH_API_PATH.refresh, {
    refreshToken,
  });
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
  const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

  return await fetcher.post(AUTH_API_PATH.logout, { refreshToken });
};
