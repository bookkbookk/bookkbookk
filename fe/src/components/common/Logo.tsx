import { ReactComponent as LogoIcon } from "@assets/images/brand.svg";
import { ROUTE_PATH } from "routes/constants";
import * as S from "../Landing/Landing.style";

export default function Logo({
  isTitleVisible = true,
}: {
  isTitleVisible?: boolean;
}) {
  return (
    <S.LogoContainer to={ROUTE_PATH.main}>
      <LogoIcon />
      {isTitleVisible && <S.LogoTitle>북크북크</S.LogoTitle>}
    </S.LogoContainer>
  );
}
