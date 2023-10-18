import Logo from "@components/common/Logo";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { GoogleLoginButton } from "../common/GoogleLoginButton";
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
