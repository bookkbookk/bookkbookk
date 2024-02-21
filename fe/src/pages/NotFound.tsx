import NotFoundImage from "@assets/images/404.png";
import Header from "@components/Landing/Header";
import * as S from "@components/common/StatusIndicator/StatusIndicator.style";
import { MESSAGE } from "@constant/index";
import { Box, Button, Stack } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function NotFound() {
  const navigate = useNavigate();

  return (
    <Box>
      <Header />
      <Stack gap={5} alignItems="center" padding={5}>
        <img src={NotFoundImage} />
        <S.Message>{MESSAGE.NOT_FOUND}</S.Message>
        <Box sx={{ display: "flex", gap: "1rem" }}>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(ROUTE_PATH.main, { replace: true })}>
            메인으로 돌아가기
          </Button>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(-1)}>
            이전 페이지로 돌아가기
          </Button>
        </Box>
      </Stack>
    </Box>
  );
}
