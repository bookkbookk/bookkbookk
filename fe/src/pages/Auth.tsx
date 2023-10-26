import { useOAuthLogin } from "@api/auth/queries";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { ACCESS_TOKEN_KEY, MESSAGE, REFRESH_TOKEN_KEY } from "@constant/index";
import { Box } from "@mui/material";
import { useEffect } from "react";
import { Navigate, useParams, useSearchParams } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useSetIsLogin } from "store/useMember";

export default function Auth() {
  const setIsLogin = useSetIsLogin();

  const { provider } = useParams();
  const [searchParams] = useSearchParams();
  const authCode = searchParams.get("code");

  const {
    data: authInfo,
    isError,
    isSuccess,
    isLoading,
  } = useOAuthLogin({
    provider: provider!,
    authCode: authCode!,
  });

  useEffect(() => {
    if (isSuccess) {
      localStorage.setItem(ACCESS_TOKEN_KEY, authInfo.data.accessToken);
      localStorage.setItem(REFRESH_TOKEN_KEY, authInfo.data.refreshToken);
      setIsLogin(true);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isSuccess]);

  return (
    <Box sx={{ height: "100vh" }}>
      {isLoading && (
        <StatusIndicator status="loading" message={MESSAGE.LOGIN_LOADING} />
      )}
      {isError && (
        <StatusIndicator status="error" message={MESSAGE.LOGIN_ERROR} />
      )}
      {isSuccess && !authInfo.data.isNewMember && (
        <Navigate to={ROUTE_PATH.main} replace />
      )}
      {isSuccess && authInfo.data.isNewMember && (
        <Navigate to={ROUTE_PATH.signUp} replace />
      )}
    </Box>
  );
}
