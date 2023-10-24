import { stringify } from "qs";

const { VITE_APP_API_URL, VITE_OAUTH_GOOGLE_CLIENT_ID } = import.meta.env;

const GOOGLE_AUTHORIZE_URI = "https://accounts.google.com/o/oauth2/v2/auth";
const GOOGLE_AUTH_PATH_VARIABLE = {
  client_id: VITE_OAUTH_GOOGLE_CLIENT_ID,
  redirect_uri: `http://localhost:5173/auth/google`,
  response_type: "code",
  scope:
    "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
};

export const GOOGLE_OAUTH_PATH = `${GOOGLE_AUTHORIZE_URI}?${stringify(
  GOOGLE_AUTH_PATH_VARIABLE
)}`;

export const ERROR_CODE = {
  UNAUTHORIZED: 401,
};

export const AUTH_API_PATH = {
  reissueToken: "/api/auth/reissue",
  login: "/api/auth/login",
  logout: "/api/auth/logout",
};

export const BOOK_CLUB_API_PATH = {
  bookClubs: "/api/book-clubs",
};

export const MEMBER_API_PATH = {
  member: "/api/members",
};

export const ALADIN_API_PATH = {
  search: "/api/aladin/books",
};

export const BOOK_API_PATH = {
  books: "/api/books",
};
