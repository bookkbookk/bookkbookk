import { postRefreshToken } from "@api/auth/client";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";

export function logout() {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(REFRESH_TOKEN_KEY);

  window.location.reload();
}

export async function reissueAccessToken() {
  const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

  if (!refreshToken) {
    return null;
  }

  try {
    const { data } = await postRefreshToken(refreshToken);
    return data.accessToken;
  } catch (error) {
    logout();
    return null;
  }
}
