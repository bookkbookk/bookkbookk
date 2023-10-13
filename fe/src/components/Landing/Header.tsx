import { ReactComponent as GoogleIcon } from "@assets/icons/google.svg";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import Logo from "./Logo";
import * as S from "./index.style";

export default function Header() {
  return (
    <S.Header>
      <Logo />
      <S.Wrapper>
        <ThemeSwitch />
        <Link to={ROUTE_PATH.OAuth}>
          <Button variant="outlined" color="inherit">
            <GoogleIcon />
            <S.ButtonText>구글로 로그인하기</S.ButtonText>
          </Button>
        </Link>
      </S.Wrapper>
    </S.Header>
  );
}
