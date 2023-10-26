import { BoxHeader, MainBox } from "@components/common/common.style";
import { useParams } from "react-router-dom";

export default function BookDetail() {
  const { bookId } = useParams<{ bookId: string }>();

  return (
    <MainBox>
      <BoxHeader />
      bookId의 챕터 목록 조회
    </MainBox>
  );
}
