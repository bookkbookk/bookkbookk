import { Navigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function Main() {
  const bookClubCode = sessionStorage.getItem("bookClubCode");

  if (bookClubCode) {
    return <Navigate to={`${ROUTE_PATH.join}/${bookClubCode}`} replace />;
  }

  return <div>메인메인</div>;
}
