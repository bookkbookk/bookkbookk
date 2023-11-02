import WelcomeImage from "@assets/images/welcome.png";
import { Button, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import Header from "./Landing/Header";
import { GoogleLoginButton } from "./common/GoogleLoginButton";

export default function BookClubJoinGuide() {
  const navigate = useNavigate();

  return (
    <Stack>
      <Header />
      <Stack
        display="flex"
        flexDirection="column"
        gap={5}
        paddingY={2}
        alignItems="center"
        justifyContent="center">
        <Typography
          variant="h2"
          component="h2"
          sx={{ fontFamily: "SOYO Maple Bold" }}>
          북크북크에 오신 걸 환영합니다!
        </Typography>
        <img src={WelcomeImage} alt="책 검색 이미지" />
        <Stack gap={1} display="flex" alignItems="center">
          <Typography variant="h4" component="h2">
            북클럽은 북크북크 유저만 참여할 수 있어요!
          </Typography>
          <Typography variant="body1">
            구글로 로그인하기 버튼을 눌러 로그인부터 진행해주세요.
          </Typography>
          <Typography variant="body1">
            로그인을 하면 바로 초대받은 북클럽으로 이동할 수 있어요.
          </Typography>
        </Stack>
        <Stack display="flex" gap={2} flexDirection="row" padding={2}>
          <GoogleLoginButton />
          <Button
            variant="outlined"
            color="primary"
            onClick={() => navigate(ROUTE_PATH.home)}>
            북크북크 서비스가 궁금해요!
          </Button>
        </Stack>
      </Stack>
    </Stack>
  );
}
