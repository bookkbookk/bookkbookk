import { BookListItem } from "@api/book/type";
import {
  BookCoverImage,
  BookDescription,
  Card,
  CardContent,
} from "@components/common/common.style";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { Location, useLocation } from "react-router-dom";

export default function ChapterBookCard() {
  const {
    state: { book },
  }: Location<{ book: BookListItem }> = useLocation();

  return (
    <Card>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h6">책 정보</Typography>
      </Box>
      <CardContent>
        <BookCoverImage
          src={book.cover}
          alt={book.title}
          width={80}
          height={100}
        />
        <Stack>
          <Typography variant="body1">{book.title}</Typography>
          <BookDescription variant="body2">{book.author}</BookDescription>
        </Stack>
      </CardContent>
      {/* TODO: menu component 적용해서 상태 변경하기 */}
      {book.statusId === 2 && (
        <Button variant="outlined">북클럽에서 독서를 시작합니다</Button>
      )}
      {book.statusId === 3 && (
        <Button variant="contained">북클럽에서 독서를 마칩니다</Button>
      )}
      {book.statusId === 4 && (
        <Button>북클럽에서 독서를 다시 시작합니다</Button>
      )}
    </Card>
  );
}
