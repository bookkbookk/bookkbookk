import { GOOGLE_OAUTH_PATH } from "@api/auth/constants";
import { ReactComponent as GoogleIcon } from "@assets/icons/google.svg";
import { ReactComponent as LogoIcon } from "@assets/images/brand-circle.svg";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import * as S from "./index.style";

export default function Header() {
  return (
    <S.Header>
      <Logo />
      <S.Wrapper>
        <ThemeSwitch />
        <GoogleLoginButton />
      </S.Wrapper>
    </S.Header>
  );
}

function Logo() {
  return (
    <S.LogoContainer to={ROUTE_PATH.main}>
      <LogoIcon />
      <S.LogoTitle>북크북크</S.LogoTitle>
    </S.LogoContainer>
  );
}

function GoogleLoginButton() {
  return (
    <Link to={GOOGLE_OAUTH_PATH}>
      <Button variant="outlined" color="inherit">
        <GoogleIcon />
        <S.ButtonText>구글로 로그인하기</S.ButtonText>
      </Button>
    </Link>
  );
}
