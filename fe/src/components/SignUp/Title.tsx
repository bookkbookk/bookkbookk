import { Typography } from "@mui/material";
import * as S from "./index.style";

export default function Title() {
  return (
    <S.TitleWrapper>
      <Typography variant="h3">안녕하세요, 반가워요! 👋</Typography>
      <Typography variant="body1">
        원하시는 프로필 사진을 등록하고, 닉네임을 입력해주세요!
      </Typography>
    </S.TitleWrapper>
  );
}
