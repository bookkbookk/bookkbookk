export type Member = {
  id: number;
  nickname: string;
  email: string;
  profileImgUrl: string;
};

export type MemberInfo = {
  nickname?: string;
  profileImage?: File;
};
