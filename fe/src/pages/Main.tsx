import { Navigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function Main() {
  const invitationCode = sessionStorage.getItem("invitationCode");

  if (invitationCode) {
    return <Navigate to={`${ROUTE_PATH.join}/${invitationCode}`} replace />;
  }

  return <div>메인메인</div>;
}
