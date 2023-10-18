import { Member } from "@api/member/type";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetMember } from "store/useMember";

export default function UserProvider() {
  const memberInfo = useLoaderData();
  const setMember = useSetMember();

  if (memberInfo) {
    setMember(memberInfo as Member);
  }

  return <Outlet />;
}
