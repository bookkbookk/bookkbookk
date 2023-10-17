import { Member } from "@api/member/type";
import { useEffect } from "react";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetIsLogin, useSetMember } from "store/useMember";

export default function UserProvider() {
  const setIsLogin = useSetIsLogin();
  const setMember = useSetMember();

  const memberInfo = useLoaderData();

  useEffect(() => {
    if (memberInfo) {
      setIsLogin(true);
      setMember(memberInfo as Member);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [memberInfo]);

  return <Outlet />;
}
