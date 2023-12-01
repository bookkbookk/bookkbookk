import { ROUTE_PATH } from "routes/constants";
export const ACCESS_TOKEN_KEY = "bookk-accessToken";

export const MESSAGE = {
  LOGIN_LOADING: `로그인중이에요... \n 새로고침을 하지 마세요!`,
  LOGIN_ERROR: `로그인에 실패했어요. \n 나중에 다시 시도해주세요.`,
  MEMBER_ERROR: `회원정보를 불러오는데 실패했어요. \n 나중에 다시 시도해주세요.`,
  MEMBER_UPDATE_ERROR: `회원정보 변경에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NICKNAME_DUPLICATED: `이미 사용중인 닉네임이에요. 다른 닉네임을 사용해주세요.`,
  NEW_BOOK_CLUB_ERROR: `북클럽 생성에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NEW_BOOK_CLUB_SUCCESS: `축하합니다! \n 앞으로 북크북크와 함께 좋은 책들을 많이 만나게 될 거에요!`,
  NEW_BOOK_ALERT: `추가할 책과 북클럽은 반드시 선택해주세요!`,
  NEW_BOOK_ERROR: `책 추가에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NEW_BOOK_SUCCESS: `책 추가를 완료했어요! 이제 책을 읽으며 챕터를 추가해볼까요?`,
  NEW_CHAPTER_ERROR: `챕터 추가에 실패했어요. \n 나중에 다시 시도해주세요.`,
  BOOK_LIST_ERROR: `책 목록을 불러오는데 실패했어요. \n 나중에 다시 시도해주세요.`,
  SEND_EMAIL_ERROR: `북클럽 초대장 전송에 실패했어요. \n 나중에 다시 시도해주세요.`,
  SEND_EMAIL_SUCCESS: `멤버들에게 북클럽 초대장 전송을 완료했어요! \n 멤버들이 초대장을 수락하도록 알려주세요.`,
  INVALID_NEW_CHAPTERS: "챕터와 토픽의 제목을 빠짐없이 모두 입력해주세요!",
  BOOK_CLUB_JOIN_LOADING:
    "북크북크 북클럽에 오신걸 환영합니다! \n 북클럽에 등록중이니 잠시만 기다려주세요!",
  BOOK_CLUB_JOIN_ERROR: `북클럽 가입에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NOT_FOUND: `요청하신 페이지를 찾을 수 없어요. \n 다른 페이지로 이동해주세요.`,
  NEW_GATHERING_ERROR: `모임 생성에 실패했어요. 나중에 다시 시도해주세요. 우선 챕터를 추가해볼까요?`,
  UPDATE_BOOK_STATUS_ERROR: `책 상태 변경에 실패했어요. \n 나중에 다시 시도해주세요.`,
  UPDATE_CHAPTER_STATUS_ERROR: `챕터 상태 변경에 실패했어요. \n 나중에 다시 시도해주세요.`,
  UPDATE_CHAPTER_TITLE_ERROR: `챕터 제목 변경에 실패했어요. \n 나중에 다시 시도해주세요.`,
  UPDATE_TOPIC_TITLE_ERROR: `토픽 제목 변경에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NEW_BOOKMARK_ERROR: `북마크 추가에 실패했어요. \n 나중에 다시 시도해주세요.`,
  NEW_COMMENT_ERROR: `댓글 추가에 실패했어요. \n 나중에 다시 시도해주세요.`,
};

export const PAGE_TITLE = {
  [ROUTE_PATH.main]: {
    korean: "북크북크",
    english: "Main",
  },
  [ROUTE_PATH.library]: {
    korean: "서재",
    english: "Library",
  },
  [ROUTE_PATH.newBook]: {
    korean: "책 추가",
    english: "New Book",
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
