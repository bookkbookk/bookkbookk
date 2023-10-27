import {
  ALADIN_API_PATH,
  AUTH_API_PATH,
  BOOK_API_PATH,
  BOOK_CLUB_API_PATH,
  MEMBER_API_PATH,
} from "@api/constants";
import { rest } from "msw";
import { BOOK_CLUB_LIST, MEMBER_INFO } from "./data";
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

  rest.get(BOOK_API_PATH.books, async (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        pagination: {
          totalItemCounts: 5,
          totalPageCounts: 2,
          currentPageIndex: 2,
        },
        books: [
          {
            id: 1,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 2,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 3,
            isbn: "9788991268807",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "프로그래머의 길, 멘토에게 묻다",
            cover:
              "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
            author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
            category:
              "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
          },
          {
            id: 4,
            isbn: "9791168341364",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title:
              "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
            cover:
              "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
            author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
            category: "국내도서>인문학>철학 일반>교양 철학",
          },
          {
            id: 5,
            isbn: "9791130645278",
            bookClub: {
              id: 3,
              name: "내꿈은농부!",
            },
            title:
              "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
            cover:
              "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
            author: "정민우(달천) (지은이)",
            category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
          },
          {
            id: 11,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 12,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 13,
            isbn: "9788991268807",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "프로그래머의 길, 멘토에게 묻다",
            cover:
              "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
            author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
            category:
              "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
          },
          {
            id: 14,
            isbn: "9791168341364",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title:
              "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
            cover:
              "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
            author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
            category: "국내도서>인문학>철학 일반>교양 철학",
          },
          {
            id: 15,
            isbn: "9791130645278",
            bookClub: {
              id: 3,
              name: "내꿈은농부!",
            },
            title:
              "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
            cover:
              "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
            author: "정민우(달천) (지은이)",
            category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
          },
          {
            id: 21,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 22,
            isbn: "9788966262335",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "함께 자라기 - 애자일로 가는 길",
            cover:
              "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
            author: "김창준 (지은이)",
            category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
          },
          {
            id: 23,
            isbn: "9788991268807",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title: "프로그래머의 길, 멘토에게 묻다",
            cover:
              "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
            author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
            category:
              "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
          },
          {
            id: 24,
            isbn: "9791168341364",
            bookClub: {
              id: 1,
              name: "낙감귤조이",
            },
            title:
              "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
            cover:
              "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
            author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
            category: "국내도서>인문학>철학 일반>교양 철학",
          },
          {
            id: 25,
            isbn: "9791130645278",
            bookClub: {
              id: 3,
              name: "내꿈은농부!",
            },
            title:
              "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
            cover:
              "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
            author: "정민우(달천) (지은이)",
            category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
          },
        ],
      })
    );
  }),
];
