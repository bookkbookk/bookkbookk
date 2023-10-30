import { postRefreshToken } from "@api/auth/client";
import { setAccessToken } from "@api/fetcher";
import { REFRESH_TOKEN_KEY } from "constant/index";
import { ROUTE_PATH } from "routes/constants";

export function logout() {
  setAccessToken("");
  localStorage.removeItem(REFRESH_TOKEN_KEY);

  window.location.href = ROUTE_PATH.main;
}

export async function reissueAccessToken() {
  const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

  if (!refreshToken) {
    return null;
  }

  try {
    const { data } = await postRefreshToken();
    return data.accessToken;
  } catch (error) {
    logout();
    return null;
  }
}
