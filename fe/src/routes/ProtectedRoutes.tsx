import { Navigate, Outlet } from "react-router-dom";
import { useIsLoginValue } from "store/useMember";
import { ROUTE_PATH } from "./constants";

export default function ProtectedRoutes() {
  const isLogin = useIsLoginValue();

  return isLogin ? <Navigate to={ROUTE_PATH.main} /> : <Outlet />;
}
