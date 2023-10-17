import { logout } from "@utils/index";
import axios from "axios";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";
import { postRefreshToken } from "./auth/client";
import { ERROR_CODE } from "./auth/constants";

const { VITE_APP_API_URL } = import.meta.env;

export const fetcher = axios.create({
  baseURL: VITE_APP_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

fetcher.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem(ACCESS_TOKEN_KEY);
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

// TODO: 리팩토링
fetcher.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (
      error.response.status === ERROR_CODE.UNAUTHORIZED &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true;

      try {
        await reissueAccessToken();
        return fetcher(originalRequest);
      } catch {
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);

async function reissueAccessToken() {
  const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

  if (!refreshToken) {
    return;
  }

  try {
    const { data } = await postRefreshToken(refreshToken);
    localStorage.setItem(ACCESS_TOKEN_KEY, data.accessToken);
  } catch (error) {
    logout();
  }
}
