import { BoxHeader, MainBox } from "@components/common/common.style";

export default function BookChapters() {
  // const { bookId } = useParams<{ bookId: string }>();

  return (
    <MainBox>
      <BoxHeader />
      bookId의 챕터 목록 조회
    </MainBox>
  );
}
