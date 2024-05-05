import axios from "axios";
import { ACCESS_TOKEN_KEY } from "constant/index";

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
