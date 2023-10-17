import { reissueAccessToken } from "@api/auth/utils";
import axios from "axios";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";
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
    const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

    if (
      error.response.status === ERROR_CODE.UNAUTHORIZED &&
      !originalRequest._retry &&
      !!refreshToken
    ) {
      originalRequest._retry = true;

      try {
        const accessToken = await reissueAccessToken();
        accessToken && localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);

        return fetcher(originalRequest);
      } catch {
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);
