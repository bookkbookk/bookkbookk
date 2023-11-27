import { BookChapterStatusID, BookListItem } from "@api/book/type";
import StatusChip from "@components/common/StatusChip";
import {
  BookCoverImage,
  BookDescription,
  Card,
  CardContent,
} from "@components/common/common.style";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import { Location, useLocation } from "react-router-dom";
import BookStatusMenu from "./BookStatusMenu";

export default function ChapterBookCard() {
  const {
    state: { book },
  }: Location<{ book: BookListItem }> = useLocation();
  const [statusId, setStatusId] = useState(book.statusId);

  const onChangeBookStatus = (statusId: BookChapterStatusID) => {
    setStatusId(statusId);
  };

  return (
    <Card>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h6">북클럽 책 정보</Typography>
        <BookStatusMenu
          {...{ bookId: book.id, statusId, onChangeBookStatus }}
        />
      </Box>
      <StatusChip statusId={statusId} />
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
    </Card>
  );
}
