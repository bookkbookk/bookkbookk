import { ROUTE_PATH } from "routes/constants";
export const ACCESS_TOKEN_KEY = "bookk-accessToken";
export const REFRESH_TOKEN_KEY = "bookk-refreshToken";

export const MESSAGE = {
  LOGIN_LOADING: `로그인중이에요... \n 새로고침을 하지 마세요!`,
  LOGIN_ERROR: `로그인에 실패했어요. \n 잠시 후 다시 시도해주세요.`,
  MEMBER_ERROR: `회원정보를 불러오는데 실패했어요. \n 잠시 후 다시 시도해주세요.`,
  MEMBER_UPDATE_ERROR: `회원정보 변경에 실패했어요. \n 잠시 후 다시 시도해주세요.`,
  NICKNAME_DUPLICATED: `이미 사용중인 닉네임이에요. 다른 닉네임을 사용해주세요.`,
  NEW_BOOK_CLUB_ERROR: `북클럽 생성에 실패했어요. \n 잠시 후 다시 시도해주세요.`,
  NEW_BOOK_CLUB_SUCCESS: `축하합니다! \n 앞으로 북크북크와 함께 좋은 책들을 많이 만나게 될 거에요! \n\n 그럼 바로 책을 추가하러 가볼까요?`,
};

export const PAGE_TITLE = {
  [ROUTE_PATH.main]: {
    korean: "메인",
    english: "Main",
  },
  [ROUTE_PATH.library]: {
    korean: "서재",
    english: "Library",
  },
  [ROUTE_PATH.bookClub]: {
    korean: "북클럽",
    english: "BookClub",
  },
  [ROUTE_PATH.myPage]: {
    korean: "마이페이지",
    english: "MyPage",
  },
  [ROUTE_PATH.newBookClub]: {
    korean: "북클럽 생성",
    english: "New BookClub",
  },
};
