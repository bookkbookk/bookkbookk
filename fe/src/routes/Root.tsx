import { ACCESS_TOKEN_KEY } from "@constant/index";
import { useEffect } from "react";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetIsLogin } from "store/useMember";

export default function Root() {
  const setIsLogin = useSetIsLogin();
  const accessToken = useLoaderData();

  // TODO: loader를 써도 깜빡임이 있는데, useEffect를 안쓰면 Warning 해결해야 함
  useEffect(() => {
    if (typeof accessToken === "string") {
      localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
      setIsLogin(true);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Outlet />;
}
