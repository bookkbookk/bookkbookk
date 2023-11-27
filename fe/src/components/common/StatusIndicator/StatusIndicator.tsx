import ErrorImage from "@assets/images/error.png";
import HeartImage from "@assets/images/heart.png";
import LoadingImage from "@assets/images/loading.gif";
import { Box, Button } from "@mui/material";
import { ROUTE_PATH } from "routes/constants";
import * as S from "./StatusIndicator.style";

export default function StatusIndicator({
  status,
  message,
}: {
  status: "loading" | "error" | "developing";
  message?: string;
}) {
  const indicatorImage = getIndicatorImage(status);

  return (
    <S.Wrapper>
      {indicatorImage}
      <S.Message>{message}</S.Message>
      {status === "error" && (
        <Box sx={{ display: "flex", gap: "1rem" }}>
          <Button variant="contained" color="primary" href={ROUTE_PATH.main}>
            메인으로 돌아가기
          </Button>
          <Button
            variant="contained"
            color="primary"
            href={window.location.href}>
            다시 시도하기
          </Button>
        </Box>
      )}
    </S.Wrapper>
  );
}

function getIndicatorImage(status: "loading" | "error" | "developing") {
  switch (status) {
    case "loading":
      return <img src={LoadingImage} />;
    case "error":
      return <img src={ErrorImage} />;
    case "developing":
      return <img src={HeartImage} />;
    default:
      return null;
  }
}
