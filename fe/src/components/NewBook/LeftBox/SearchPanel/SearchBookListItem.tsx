import { BookInfo as BookInfoType } from "@api/book/type";
import * as S from "@components/NewBook/style";
import { Typography } from "@mui/material";

export function SearchBookListItem(bookInfo: BookInfoType) {
  const { title, author, pubDate, description, cover, category, publisher } =
    bookInfo;

  return (
    <S.SearchBookListItem>
      <S.BookCoverImage
        src={cover}
        alt={title}
        sx={{
          minWidth: "90px",
          maxWidth: "90px",
          minHeight: "120px",
          maxHeight: "120px",
        }}
      />
      <S.BookInfo>
        <S.BookTitle variant="h6">{title}</S.BookTitle>
        <S.BookSubInfoWrapper>
          <Typography variant="body2">{author}</Typography>
          <Typography variant="body2">{publisher}</Typography>
          <Typography variant="body2">{pubDate}</Typography>
        </S.BookSubInfoWrapper>
        <Typography variant="body2">{category}</Typography>
        <S.BookDescription variant="body2">{description}</S.BookDescription>
      </S.BookInfo>
    </S.SearchBookListItem>
  );
}
