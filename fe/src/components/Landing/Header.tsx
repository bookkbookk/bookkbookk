import Logo from "@components/common/Logo";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Wrapper } from "@components/common/common.style";
import { GoogleLoginButton } from "../common/GoogleLoginButton";
import * as S from "./Landing.style";

export default function Header() {
  return (
    <S.Header>
      <Logo />
      <Wrapper>
        <ThemeSwitch />
        <GoogleLoginButton />
      </Wrapper>
    </S.Header>
  );
}
