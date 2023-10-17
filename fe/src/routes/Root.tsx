import { ACCESS_TOKEN_KEY } from "@constant/index";
import { Outlet, useLoaderData } from "react-router-dom";
import { useSetIsLogin } from "store/useMember";

export default function Root() {
  const setIsLogin = useSetIsLogin();
  const accessToken = useLoaderData();

  if (typeof accessToken === "string") {
    localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
    setIsLogin(true);
  }

  return <Outlet />;
}
