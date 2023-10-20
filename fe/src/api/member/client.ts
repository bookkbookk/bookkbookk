import { fetcher, formDataConfig } from "@api/fetcher";
import { makeFormData } from "@api/utils";
import { MEMBER_API_PATH } from "../constants";
import { Member, MemberInfo } from "./type";

export const getMember = async () => {
  const { data } = await fetcher.get<Member>(MEMBER_API_PATH.member);
  return data;
};

export const patchMemberInfo = async (memberInfo: MemberInfo) => {
  const memberInfoFormData = makeFormData(memberInfo);

  const { data } = await fetcher.patch(
    `${MEMBER_API_PATH.member}/profile`,
    memberInfoFormData,
    formDataConfig
  );
  return data;
};
