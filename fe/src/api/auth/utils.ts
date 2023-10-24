import { postRefreshToken } from "@api/auth/client";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";
import { ROUTE_PATH } from "routes/constants";

export function logout() {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
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
