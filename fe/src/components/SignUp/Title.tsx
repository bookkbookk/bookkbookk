import { Typography } from "@mui/material";
import * as S from "./SignUp.style";

export default function Title() {
  return (
    <S.TitleWrapper>
      <Typography sx={{ fontFamily: "SOYO Maple Bold" }} variant="h2">
        안녕하세요, 반가워요! 👋
      </Typography>
      <Typography variant="h6">
        원하시는 프로필 사진을 등록하고, 닉네임을 입력해주세요!
      </Typography>
    </S.TitleWrapper>
  );
}
