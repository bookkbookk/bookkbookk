import { ROUTE_PATH } from "routes/constants";
export const ACCESS_TOKEN_KEY = "bookk-accessToken";
export const REFRESH_TOKEN_KEY = "bookk-refreshToken";

export const MESSAGE = {
  LOGIN_LOADING: `로그인중입니다... \n 새로고침을 하지 마세요!`,
  LOGIN_ERROR: `로그인에 실패하였습니다. \n 잠시 후 다시 시도해주세요.`,
  MEMBER_ERROR: `회원정보를 불러오는데 실패하였습니다. \n 잠시 후 다시 시도해주세요.`,
  MEMBER_UPDATE_ERROR: `회원정보 변경에 실패하였습니다. \n 잠시 후 다시 시도해주세요.`,
  NICKNAME_DUPLICATED: `이미 사용중인 닉네임이에요. 다른 닉네임을 사용해주세요.`,
};

export const PAGE_TITLE = {
  [ROUTE_PATH.main]: "메인",
  [ROUTE_PATH.library]: "서재",
  [ROUTE_PATH.bookClub]: "북클럽",
};
