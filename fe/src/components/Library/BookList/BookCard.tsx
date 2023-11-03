import { BookListItem } from "@api/book/type";
import { BookCoverImage, ImageWrapper } from "@components/NewBook/style";
import { CardContent, Chip, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import * as S from "../Library.style";

export default function BookCard({ book }: { book: BookListItem }) {
  const { bookClub, title, cover, author } = book;
  const navigate = useNavigate();

  return (
    <S.BookCard
      variant="outlined"
      onClick={() =>
        navigate(`${ROUTE_PATH.chapters}/${book.id}`, {
          state: { book },
        })
      }>
      <CardContent>
        <Stack sx={{ display: "flex", alignItems: "center" }}>
          <Chip
            label={bookClub.name}
            size="small"
            sx={{ width: "100%", fontWeight: 700 }}
          />
          <ImageWrapper sx={{ margin: "1rem" }}>
            <BookCoverImage
              sx={{ margin: "1rem" }}
              src={cover}
              alt={title}
              width={90}
              height={140}
            />
          </ImageWrapper>
        </Stack>
        <Typography variant="h6" component="div">
          {title}
        </Typography>
        <Typography variant="body1" color="text.secondary">
          {author}
        </Typography>
      </CardContent>
    </S.BookCard>
  );
}
