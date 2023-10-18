export type Tokens = {
  accessToken: string;
  refreshToken: string;
};

export type OAuthLoginParams = {
  provider: string;
  authCode: string;
};
