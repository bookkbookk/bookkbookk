import ErrorImage from "@assets/images/error.svg";
import LoadingImage from "@assets/images/loading.gif";
import { Button } from "@mui/material";
import { Message, Wrapper } from "./index.style";

export default function StatusIndicator({
  status,
  message,
}: {
  status: "loading" | "error";
  message?: string;
}) {
  const indicatorImage = getIndicatorImage(status);

  return (
    <Wrapper>
      {indicatorImage}
      <Message>{message}</Message>
      {status === "error" && (
        <Button variant="contained" color="primary" href={window.location.href}>
          다시 시도하기
        </Button>
      )}
    </Wrapper>
  );
}

function getIndicatorImage(status: "loading" | "error") {
  switch (status) {
    case "loading":
      return <img src={LoadingImage} />;
    case "error":
      return <img src={ErrorImage} />;
    default:
      return null;
  }
}
