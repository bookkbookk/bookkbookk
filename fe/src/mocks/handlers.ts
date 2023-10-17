import { AUTH_API_PATH } from "@api/auth/constants";
import { MEMBER_API_PATH } from "@api/member/constants";
import { rest } from "msw";
import { MEMBER_INFO } from "./data";

const TOKEN_EXPIRATION = {
  accessToken: false,
  refreshToken: true,
};

export const handlers = [
  rest.get("/api/endpoint", (req, res, ctx) => {
    return res(ctx.json({ data: "data" }));
  }),

  rest.get(MEMBER_API_PATH.member, (req, res, ctx) => {
    const Authorization = req.headers.get("Authorization");

    if (!Authorization) {
      return res(
        ctx.status(401),
        ctx.json({
          message: "잘못된 요청입니다.",
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

  rest.get(AUTH_API_PATH.refresh, async (req, res, ctx) => {
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

    TOKEN_EXPIRATION.accessToken = false;
    return res(
      ctx.status(200),
      ctx.json<{ accessToken: string }>({
        accessToken: "newAccessToken",
      })
    );
  }),

  rest.post(`${AUTH_API_PATH.login}/:provider`, async (req, res, ctx) => {
    const { OAuthCode } = await req.json<{ OAuthCode: string }>();

    if (!OAuthCode) {
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
];
