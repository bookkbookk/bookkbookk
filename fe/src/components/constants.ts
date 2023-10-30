export const NEW_BOOK_CLUB_FUNNEL = {
  profile: "profile" as const,
  member: "member" as const,
  congratulation: "congratulation" as const,
};

export const NEW_BOOK_FUNNEL = {
  bookSearch: "bookSearch" as const,
  bookClubGathering: "bookClubGathering" as const,
};

export const BOOK_CHAPTERS_TAB = [
  {
    id: 0,
    label: "전체 챕터",
  },
  {
    id: 1,
    label: "독서 전 챕터",
  },
  {
    id: 2,
    label: "독서 중 챕터",
  },
  {
    id: 3,
    label: "독서 완료 챕터",
  },
];

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
