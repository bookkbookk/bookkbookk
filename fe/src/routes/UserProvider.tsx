import { Member } from "@api/member/type";
import { useEffect } from "react";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetMember } from "store/useMember";

export default function UserProvider() {
  const memberInfo = useLoaderData();
  const setMember = useSetMember();

  useEffect(() => {
    setMember({
      type: "INIT",
      payload: memberInfo as Member,
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Outlet />;
}
