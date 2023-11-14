import { postRefreshToken } from "@api/auth/client";

export async function reissueAccessToken() {
  try {
    const { data } = await postRefreshToken();

    return data.accessToken;
  } catch (error) {
    return null;
  }
}
