import { useGetChapters } from "@api/chapters/queries";
import Paper from "@mui/material/Paper";
import { useParams } from "react-router-dom";
import { ChapterFieldHeader } from "./ChapterFieldHeader";
import { ChapterTree } from "./ChapterTree";

export default function BookChapterListPanel({
  statusId,
}: {
  statusId: number;
}) {
  const { bookId } = useParams<{ bookId: string }>();
  const chapterList = useGetChapters({
    bookId: Number(bookId),
    statusId,
  });

  return (
    <Paper
      sx={{
        width: "100%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
      }}>
      <ChapterFieldHeader />
      <ChapterTree chapterList={chapterList} />
    </Paper>
  );
}
