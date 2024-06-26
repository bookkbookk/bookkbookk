import * as S from "@components/common/common.style";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

// TODO: 페이지 뷰 작업 완료되면 기능 단위로 서비스 소개
export default function Main() {
  return (
    <S.Section>
      <S.SectionContent>
        <S.SectionTitle>함께 읽고, 나누고, 기록해요!</S.SectionTitle>
        <S.SectionDescription>
          북크북크는 독서모임을 하는 사람들끼리 함께 독서를 즐기고, 독서기록을
          공유하는 공간입니다.
        </S.SectionDescription>
        <Link to={ROUTE_PATH.main}>
          <Button variant="contained" color="primary">
            북크북크 시작하기
          </Button>
        </Link>
      </S.SectionContent>
    </S.Section>
  );
}
