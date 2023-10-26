import { BookListItem } from "@api/book/type";
import { BookCoverImage, ImageWrapper } from "@components/NewBook/style";
import { Card, CardContent, Chip, Stack, Typography } from "@mui/material";

export default function BookCard({ book }: { book: BookListItem }) {
  const { bookClub, title, cover, author } = book;

  return (
    <Card variant="outlined">
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
    </Card>
  );
}
