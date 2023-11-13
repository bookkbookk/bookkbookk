import { useGetChapters } from "@api/chapters/queries";
import { BookChapterStatus } from "@api/chapters/type";
import { useParams } from "react-router-dom";

export default function BookChapterListPanel({
  statusId,
}: {
  statusId: BookChapterStatus["id"];
}) {
  const { bookId } = useParams<{ bookId: string }>();
  const chapters = useGetChapters({
    bookId: Number(bookId),
    statusId,
  });
  // TODO: chapters로 챕터 목록을 렌더링
  console.log(chapters);

  return (
    <div>
      <span>
        bookId: {bookId} statusId: {statusId} 챕터 목록 렌더링
      </span>
    </div>
  );
}
