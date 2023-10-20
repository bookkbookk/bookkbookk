import { stringify } from "qs";

const { VITE_APP_API_URL, VITE_OAUTH_GOOGLE_CLIENT_ID } = import.meta.env;
const GOOGLE_AUTHORIZE_URI = "https://accounts.google.com/o/oauth2/v2/auth";
const GOOGLE_AUTH_PATH_VARIABLE = {
  client_id: VITE_OAUTH_GOOGLE_CLIENT_ID,
  redirect_uri: `${VITE_APP_API_URL}/auth/google`,
  response_type: "code",
  scope:
    "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
};

export const GOOGLE_OAUTH_PATH = `${GOOGLE_AUTHORIZE_URI}?${stringify(
  GOOGLE_AUTH_PATH_VARIABLE
)}`;

export const AUTH_API_PATH = {
  refresh: "/api/auth/reissue",
  login: "/api/auth/login",
  logout: "/api/auth/logout",
};

export const ERROR_CODE = {
  UNAUTHORIZED: 401,
};
