import { fetcher } from "@api/fetcher";
import { MEMBER_API_PATH } from "./constants";
import { Member } from "./type";

export const getMember = async () => {
  const { data } = await fetcher.get<Member>(MEMBER_API_PATH.member);
  return data;
};
