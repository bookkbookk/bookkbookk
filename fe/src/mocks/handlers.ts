import {
  ALADIN_API_PATH,
  AUTH_API_PATH,
  BOOK_API_PATH,
  BOOK_CLUB_API_PATH,
  GATHERING_API_PATH,
  MEMBER_API_PATH,
} from "@api/constants";
import { rest } from "msw";
import {
  BOOKMARKS,
  BOOK_CLUB_DETAIL_OPEN,
  BOOK_CLUB_LIST,
  CHAPTER_LIST,
  COMMENTS,
  MEMBER_INFO,
  USER_BOOK_LIST,
} from "./data";
import ALADIN_BOOK_SEARCH_EXAMPLE from "./data.json";

const TOKEN_EXPIRATION = {
  accessToken: false,
  refreshToken: false,
};

// eslint-disable-next-line
let TOPIC_LIST = [
  {
    topicId: 1,
    title: "id 1 첫번째 토픽",
  },
  {
    topicId: 52,
    title: "id 52 두번째 토픽",
  },
];

export const handlers = [
  rest.get(MEMBER_API_PATH.member, (req, res, ctx) => {
    const Authorization = req.headers.get("Authorization");

    if (!Authorization) {
      return res(
        ctx.status(401),
        ctx.json({
          code: 4011,
          message: "Authorization 헤더가 없습니다.",
        })
      );
    }

    if (TOKEN_EXPIRATION.accessToken) {
      return res(
        ctx.status(401),
        ctx.json({
          code: 4011,
          message: "만료된 토큰입니다.",
        })
      );
    }

    return res(ctx.status(200), ctx.json(MEMBER_INFO));
  }),

  rest.post(AUTH_API_PATH.reissueToken, async (req, res, ctx) => {
    if (!req.cookies["refreshToken"]) {
      return res(
        ctx.status(401),
        ctx.json({
          code: 4012,
          message: "리프레쉬 토큰이 없습니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json<{ accessToken: string }>({
        accessToken: "newAccessToken",
      })
    );
  }),

  rest.post(`${AUTH_API_PATH.login}/*`, async (req, res, ctx) => {
    const { authCode } = await req.json<{ authCode: string }>();

    if (!authCode) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    await new Promise((resolve) => setTimeout(resolve, 3000));

    return res(
      ctx.status(200),
      ctx.cookie("refreshToken", "iAmRefreshToken", {
        httpOnly: true,
        secure: true,
      }),
      ctx.json<{
        accessToken: string;
        isNewMember: boolean;
      }>({
        accessToken:
          "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjkxOTIyNjAzfQ.vCxUGMiv9bnb4JQGwk6NVx6kHi5hG80tDxafIvrfKbA",
        isNewMember: true,
      })
    );
  }),

  rest.patch(`${MEMBER_API_PATH.member}/profile`, (req, res, ctx) => {
    const memberInfo = req.body as FormData;

    if (!memberInfo) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    // if ("test" === memberInfo.nickname) {
    //   return res(
    //     ctx.status(409),
    //     ctx.json({
    //       errorCode: "E0009",
    //       message: "이미 사용중인 닉네임입니다.",
    //     })
    //   );
    // }

    // if (memberInfo.profileImage) {
    //   return res(
    //     ctx.status(200),
    //     ctx.json({
    //       ...MEMBER_INFO,
    //       newProfileImgUrl:
    //         "https://github.com/masters2023-project-team05-second-hand/second-hand-max-fe/assets/111998760/4ce425f1-d40b-421f-a24f-3c5b73737120",
    //     })
    //   );
    // }
  }),

  rest.post(AUTH_API_PATH.logout, async (_, res, ctx) => {
    // const { refreshToken } = await req.json<{ refreshToken: string | null }>();

    // if (!refreshToken) {
    //   return res(
    //     ctx.status(400),
    //     ctx.json({
    //       message: "잘못된 요청입니다.",
    //     })
    //   );
    // }

    return res(ctx.status(200), ctx.cookie("refreshToken", "", { maxAge: 0 }));
  }),

  rest.post(BOOK_CLUB_API_PATH.bookClubs, async (_, res, ctx) => {
    // const { name } = (await req.body) as FormData;

    // if (!name) {
    //   return res(
    //     ctx.status(400),
    //     ctx.json({
    //       message: "잘못된 요청입니다.",
    //     })
    //   );
    // }

    return res(
      ctx.status(200),
      ctx.json({
        bookClubId: 1,
        invitationUrl:
          "https://bookkbookk.site/join/6febcb9e-76ab-4852-8b38-04b3933c0538",
      })
    );
  }),

  rest.get(ALADIN_API_PATH.search, async (req, res, ctx) => {
    const search = req.url.search;

    if (!search) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "search 쿼리 파라미터가 없습니다.",
        })
      );
    }

    if (search === `?search=${encodeURIComponent("바람")}`) {
      return res(ctx.status(200), ctx.json(ALADIN_BOOK_SEARCH_EXAMPLE));
    }

    return res(ctx.status(200), ctx.json([]));
  }),

  rest.get(
    `${BOOK_CLUB_API_PATH.bookClubs}?status=open`,
    async (_, res, ctx) => {
      return res(ctx.status(200), ctx.json(BOOK_CLUB_LIST));
    }
  ),

  rest.post(BOOK_API_PATH.books, async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json({ createdBookId: 1 }));
  }),

  rest.get(MEMBER_API_PATH.books, async (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        pagination: {
          totalItemCounts: 5,
          totalPageCounts: 2,
          currentPageIndex: 2,
        },
        books: USER_BOOK_LIST,
      })
    );
  }),

  rest.post(BOOK_API_PATH.chapters, async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post(BOOK_CLUB_API_PATH.join, async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json({ bookClubId: 10 }));
  }),

  rest.get(`${BOOK_CLUB_API_PATH.bookClubs}/:bookId`, async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(BOOK_CLUB_DETAIL_OPEN));
  }),

  rest.get(`${BOOK_CLUB_API_PATH.bookClubs}/*/books`, async (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        hasNext: false,
        books: [
          {
            id: 1,
            title:
              "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
            cover:
              "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
            author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
            category: "국내도서>인문학>철학 일반>교양 철학",
          },
          {
            id: 2,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 3,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 4,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 5,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 11,
            title:
              "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
            cover:
              "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
            author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
            category: "국내도서>인문학>철학 일반>교양 철학",
          },
          {
            id: 12,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 13,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 14,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 15,
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
        ],
      })
    );
  }),

  rest.post(`${GATHERING_API_PATH.gatherings}/:bookId`, async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get(`${BOOK_API_PATH.books}/*/chapters`, async (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(CHAPTER_LIST));
  }),

  rest.patch(`${BOOK_API_PATH.books}/*`, async (req, res, ctx) => {
    const { statusId } = await req.json<{ statusId: string }>();

    if (!statusId) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "statusId가 없습니다. 잘못된 요청입니다.",
        })
      );
    }

    return res(ctx.status(200));
  }),

  rest.patch(`${BOOK_API_PATH.chapters}/*`, async (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.patch(`${BOOK_API_PATH.topics}/:topicId`, async (req, res, ctx) => {
    const { title } = await req.json<{ title: string }>();
    const { topicId } = req.params;

    TOPIC_LIST = TOPIC_LIST.map((topic) => {
      if (topic.topicId === Number(topicId)) {
        return {
          ...topic,
          title,
        };
      }

      return topic;
    });

    return res(ctx.status(200));
  }),

  rest.get(
    `${BOOK_API_PATH.chapters}/:chapterId/topics`,
    async (_, res, ctx) => {
      return res(ctx.status(200), ctx.json(TOPIC_LIST));
    }
  ),

  rest.get(
    `${BOOK_API_PATH.topics}/:topicId/bookmarks`,
    async (_, res, ctx) => {
      return res(ctx.status(200), ctx.json(BOOKMARKS));
    }
  ),

  rest.get(
    `${BOOK_API_PATH.bookmarks}/:bookmarkId/comments`,
    async (_, res, ctx) => {
      return res(ctx.status(200), ctx.json(COMMENTS));
    }
  ),
];
