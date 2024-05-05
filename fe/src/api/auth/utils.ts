import { postRefreshToken } from "@api/auth/client";

export async function reissueAccessToken() {
  const cookies = document.cookie.split("; ");
  const refreshTokenCookie = cookies.find((cookie) =>
    cookie.startsWith("refreshToken")
  );

  if (!refreshTokenCookie) {
    return null;
  }

  try {
    const { data } = await postRefreshToken();

    return data.accessToken;
  } catch (error) {
    return null;
  }
}
