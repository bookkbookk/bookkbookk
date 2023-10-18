import { GOOGLE_OAUTH_PATH } from "@api/auth/constants";
import { ReactComponent as GoogleIcon } from "@assets/icons/google.svg";
import Logo from "@components/common/Logo";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import * as S from "./Landing.style";

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
