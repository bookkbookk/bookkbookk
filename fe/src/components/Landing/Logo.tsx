import { ReactComponent as LogoIcon } from "@assets/images/brand-circle.svg";
import { ROUTE_PATH } from "routes/constants";
import * as S from "./index.style";

export default function Logo() {
  return (
    <S.LogoContainer to={ROUTE_PATH.main}>
      <LogoIcon />
      <S.LogoTitle>북크북크</S.LogoTitle>
    </S.LogoContainer>
  );
}
