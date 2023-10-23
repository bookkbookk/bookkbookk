export const NEW_BOOK_CLUB_FUNNEL = {
  profile: "profile" as const,
  member: "member" as const,
  congratulation: "congratulation" as const,
};

export const NEW_BOOK_FUNNEL = {
  bookSearch: "bookSearch" as const,
  bookClubGathering: "bookClubGathering" as const,
  bookChapters: "bookChapters" as const,
};

export const BOOK_CLUB_TAB = [
  {
    id: 0,
    label: "전체 북클럽",
  },
  {
    id: 1,
    label: "열린 북클럽",
  },
  {
    id: 2,
    label: "닫힌 북클럽",
  },
];

export const NEW_BOOK_TABS = [
  {
    id: 0,
    label: "책 검색",
  },
  {
    id: 1,
    label: "북클럽 모임 일정",
  },
  {
    id: 2,
    label: "챕터 토픽 작성",
  },
];

export const TOTAL_TAB = {
  id: 0,
  label: "전체 책장",
};

export const DEFAULT_TABS = [
  {
    id: 1,
    label: "독서전",
  },
  {
    id: 2,
    label: "독서중",
  },
  {
    id: 3,
    label: "독서완료",
  },
];

export const LIBRARY_TABS = Array.from({ length: 5 }, (_, i) => ({
  id: i * 10 + 1,
  label: `USER CUSTOM TAB ${i + 1}`,
}));
