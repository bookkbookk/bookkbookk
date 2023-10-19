import { AUTH_API_PATH } from "@api/auth/constants";
import { MEMBER_API_PATH } from "@api/member/constants";
import { rest } from "msw";
import { MEMBER_INFO } from "./data";

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

    return res(ctx.json(MEMBER_INFO));
  }),

  rest.post(AUTH_API_PATH.refresh, async (req, res, ctx) => {
    const { refreshToken } = await req.json<{ refreshToken: string }>();

    if (!refreshToken) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

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

  rest.post(`${AUTH_API_PATH.login}/:provider`, async (req, res, ctx) => {
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

  rest.patch(MEMBER_API_PATH.member, (req, res, ctx) => {
    const memberInfo = req.body as FormData;

    if (!memberInfo) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    if ("test" === memberInfo.nickname) {
      return res(
        ctx.status(409),
        ctx.json({
          errorCode: "E0009",
          message: "이미 사용중인 닉네임입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json({
        memberInfo,
      })
    );
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
];
