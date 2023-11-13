import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { Box } from "@mui/material";
import { Navigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function Main() {
  const invitationCode = sessionStorage.getItem("invitationCode");

  if (invitationCode) {
    return <Navigate to={`${ROUTE_PATH.join}/${invitationCode}`} replace />;
  }

  return (
    <Box width="100%" height="100%">
      <StatusIndicator
        status="developing"
        message={`메인 페이지는 열심히 개발하고 있어요! \n Coming soon!`}
      />
    </Box>
  );
}
