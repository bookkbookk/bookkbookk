export const NEW_BOOK_CLUB_FUNNEL = {
  profile: "프로필" as const,
  member: "멤버 초대" as const,
  congratulation: "생성 완료" as const,
};

export const NEW_BOOK_FUNNEL = {
  bookSearch: "책 검색" as const,
  bookClubGathering: "북클럽 선택" as const,
};

export const NEW_GATHERING_FUNNEL = {
  bookChoice: "책 선택" as const,
  gathering: "모임 추가" as const,
  checkInfo: "모임 생성" as const,
};

export const BOOK_CHAPTER_STATUS = {
  BEFORE_READING: {
    id: 1,
    label: "독서 전",
  },
  READING: {
    id: 2,
    label: "독서 중",
  },
  AFTER_READING: {
    id: 3,
    label: "독서 완료",
  },
} as const;

export const BOOK_CHAPTER_TABS = {
  ALL: {
    id: 0,
    label: "전체",
  },
  ...BOOK_CHAPTER_STATUS,
} as const;

export const BOOK_CLUB_STATUS = {
  ALL: {
    id: 0,
    label: "전체 북클럽",
  },
  OPEN: {
    id: 1,
    label: "열린 북클럽",
  },
  CLOSE: {
    id: 2,
    label: "닫힌 북클럽",
  },
} as const;

export const BOOK_CHAPTERS_TAB = Object.values(BOOK_CHAPTER_TABS);
export const BOOK_CHAPTERS_STATUS_LIST = Object.values(BOOK_CHAPTER_STATUS);
export const BOOK_CLUB_TAB = Object.values(BOOK_CLUB_STATUS);

export const TOTAL_TAB = {
  id: 0,
  label: "전체 책장",
};

/* TODO: DB에 기본 책장으로 추가 요청 */
// export const DEFAULT_TABS = [
//   {
//     id: 1,
//     label: "독서전",
//   },
//   {
//     id: 2,
//     label: "독서중",
//   },
//   {
//     id: 3,
//     label: "독서완료",
//   },
// ];
