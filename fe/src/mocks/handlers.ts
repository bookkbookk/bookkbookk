import {
  ALADIN_API_PATH,
  AUTH_API_PATH,
  BOOK_API_PATH,
  BOOK_CLUB_API_PATH,
  MEMBER_API_PATH,
} from "@api/constants";
import { rest } from "msw";
import { BOOK_CLUB_LIST, MEMBER_INFO, USER_BOOK_LIST } from "./data";
import ALADIN_BOOK_SEARCH_EXAMPLE from "./data.json";

const TOKEN_EXPIRATION = {
  accessToken: false,
  refreshToken: false,
};

export const handlers = [
  rest.get(MEMBER_API_PATH.member, (req, res, ctx) => {
    const Authorization = req.headers.get("Authorization");

    if (!Authorization) {
      return res(
        ctx.status(401),
        ctx.json({
          errorCode: "E0001",
          message: "Authorization 헤더가 없습니다.",
        })
      );
    }

    if (TOKEN_EXPIRATION.accessToken) {
      return res(
        ctx.status(401),
        ctx.json({
          message: "만료된 토큰입니다.",
        })
      );
    }

    return res(ctx.status(200), ctx.json(MEMBER_INFO));
  }),

  rest.post(AUTH_API_PATH.reissueToken, async (_, res, ctx) => {
    if (TOKEN_EXPIRATION.refreshToken) {
      return res(
        ctx.status(401),
        ctx.json({
          message: "만료된 토큰입니다.",
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
      ctx.json<{
        accessToken: string;
        refreshToken: string;
        isNewMember: boolean;
      }>({
        accessToken:
          "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjkxOTIyNjAzfQ.vCxUGMiv9bnb4JQGwk6NVx6kHi5hG80tDxafIvrfKbA",
        refreshToken:
          "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTcxMDMwMDN9.FgoFySrenum985OrDzwwtaEhu1Iz7IVJtz5M6H8lzX8",
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

  rest.post(AUTH_API_PATH.logout, async (req, res, ctx) => {
    const { refreshToken } = await req.json<{ refreshToken: string | null }>();

    if (!refreshToken) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(ctx.status(200));
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

    return res(ctx.status(200));
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

  rest.get(BOOK_API_PATH.books, async (_, res, ctx) => {
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
];
