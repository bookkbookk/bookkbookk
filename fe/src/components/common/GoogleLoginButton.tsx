import { GOOGLE_OAUTH_PATH } from "@api/constants";
import { ReactComponent as GoogleIcon } from "@assets/icons/google.svg";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import * as S from "../Landing/Landing.style";

export function GoogleLoginButton() {
  return (
    <Link to={GOOGLE_OAUTH_PATH}>
      <Button variant="outlined" color="inherit">
        <GoogleIcon />
        <S.ButtonText>구글로 로그인하기</S.ButtonText>
      </Button>
    </Link>
  );
}
