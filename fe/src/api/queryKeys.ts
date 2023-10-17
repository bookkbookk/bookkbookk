import { createQueryKeyStore } from "@lukemorales/query-key-factory";
import { postLogin } from "./auth/client";
import { OAuthLoginParams } from "./auth/type";
import { getMember } from "./member/client";

export const queryKeys = createQueryKeyStore({
  members: {
    info: (enabled?: boolean) => ({
      queryKey: ["getMember"],
      queryFn: getMember,
      enabled,
    }),
  },
  auth: {
    login: ({ provider, OAuthCode }: OAuthLoginParams) => ({
      queryKey: ["postLogin"],
      queryFn: () => postLogin({ provider, OAuthCode }),
      enabled: !!OAuthCode && !!provider,
    }),
  },
});
