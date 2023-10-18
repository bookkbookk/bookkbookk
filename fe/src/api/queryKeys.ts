import { createQueryKeyStore } from "@lukemorales/query-key-factory";
import { postLogin } from "./auth/client";
import { OAuthLoginParams } from "./auth/type";
import { getMember } from "./member/client";

export const queryKeys = createQueryKeyStore({
  members: {
    info: (option?: { enabled?: boolean }) => ({
      queryKey: ["getMember"],
      queryFn: getMember,
      enabled: option?.enabled,
    }),
  },
  auth: {
    login: ({ provider, authCode }: OAuthLoginParams) => ({
      queryKey: ["postLogin"],
      queryFn: () => postLogin({ provider, authCode }),
      enabled: !!authCode && !!provider,
    }),
  },
});
