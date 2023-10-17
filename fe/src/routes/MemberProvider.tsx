import { Member } from "@api/member/type";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetIsLogin, useSetMember } from "store/useMember";

export default function MemberProvider() {
  const setIsLogin = useSetIsLogin();
  const setMember = useSetMember();

  const memberInfo = useLoaderData();

  if (memberInfo) {
    setIsLogin(true);
    setMember(memberInfo as Member);
  }

  return <Outlet />;
}
