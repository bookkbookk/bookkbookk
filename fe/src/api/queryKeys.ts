import { createQueryKeyStore } from "@lukemorales/query-key-factory";
import { postLogin } from "./auth/client";
import { OAuthLoginParams } from "./auth/type";

export const queryKeys = createQueryKeyStore({
  auth: {
    login: ({ provider, OAuthCode }: OAuthLoginParams) => ({
      queryKey: ["postLogin"],
      queryFn: () => postLogin({ provider, OAuthCode }),
      enabled: !!OAuthCode && !!provider,
    }),
  },
  members: {},
});
