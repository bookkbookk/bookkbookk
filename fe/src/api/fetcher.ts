import { reissueAccessToken } from "@api/auth/utils";
import axios from "axios";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";
import { AUTH_API_PATH, ERROR_CODE } from "./constants";

const { VITE_APP_API_URL } = import.meta.env;

export const fetcher = axios.create({
  baseURL: VITE_APP_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const formDataConfig = {
  headers: {
    "Content-Type": "multipart/form-data",
  },
};

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

export const fetcherWithRefreshToken = axios.create({
  baseURL: VITE_APP_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

fetcherWithRefreshToken.interceptors.request.use(
  (config) => {
    const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);
    if (refreshToken) {
      config.headers["Authorization"] = `Bearer ${refreshToken}`;
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
      originalRequest.url !== AUTH_API_PATH.reissueToken
    ) {
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
