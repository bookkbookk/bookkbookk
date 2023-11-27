import { Book } from "@api/book/type";
import { Stack, Typography } from "@mui/material";
import { BookCoverImage, BookDescription } from "./common.style";

export default function BookInfo({ book }: { book: Book }) {
  const { title, author, cover } = book;

  return (
    <Stack display="flex" flexDirection="row" gap={2}>
      <BookCoverImage src={cover} alt={title} />
      <Stack justifyContent="center">
        <Typography variant="body1">{title}</Typography>
        <BookDescription variant="body2">{author}</BookDescription>
      </Stack>
    </Stack>
  );
}
