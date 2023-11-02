import LogoImage from "@assets/images/brand.png";
import { ROUTE_PATH } from "routes/constants";
import * as S from "../Landing/Landing.style";

export default function Logo({
  isTitleVisible = true,
}: {
  isTitleVisible?: boolean;
}) {
  return (
    <S.LogoContainer to={ROUTE_PATH.main}>
      <img src={LogoImage} width={50} height={50} />
      {isTitleVisible && <S.LogoTitle>북크북크</S.LogoTitle>}
    </S.LogoContainer>
  );
}
