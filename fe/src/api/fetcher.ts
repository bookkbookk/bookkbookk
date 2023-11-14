import { reissueAccessToken } from "@api/auth/utils";
import axios from "axios";
import { ACCESS_TOKEN_KEY } from "constant/index";
import { ERROR_CODE } from "./constants";

const { VITE_APP_API_URL } = import.meta.env;

const tokenStorage = {
  [ACCESS_TOKEN_KEY]: "",
};

export const setAccessToken = (accessToken: string) => {
  tokenStorage[ACCESS_TOKEN_KEY] = accessToken;
};

export const getAccessToken = () => tokenStorage[ACCESS_TOKEN_KEY];

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
    const accessToken = tokenStorage[ACCESS_TOKEN_KEY];

    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// TODO: 리팩토링

fetcher.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response.status === ERROR_CODE.UNAUTHORIZED) {
      try {
        const accessToken = await reissueAccessToken();
        accessToken && setAccessToken(accessToken);

        return fetcher(originalRequest);
      } catch {
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);
