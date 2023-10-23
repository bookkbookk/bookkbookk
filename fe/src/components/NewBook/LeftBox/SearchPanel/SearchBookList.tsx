import { BookSearchInfo } from "@api/book/type";
import { Paper } from "@mui/material";
import * as S from "./SearchPanel.style";

export default function SearchBookList({ data }: { data: BookSearchInfo[] }) {
  return (
    <Paper>
      {data.map((bookInfo) => (
        <SearchBookListItem key={bookInfo.isbn13} {...bookInfo} />
      ))}
    </Paper>
  );
}

function SearchBookListItem(bookInfo: BookSearchInfo) {
  const {
    title,
    author,
    pubDate,
    description,
    cover,
    categoryName,
    publisher,
  } = bookInfo;

  return (
    <S.SearchBookListItem>
      <S.BookCover src={cover} alt={title} />
      <S.BookInfo>
        <S.BookTitle variant="h6">{title}</S.BookTitle>
        <S.BookSubInfoWrapper>
          <S.BookSubInfo variant="body2">{author}</S.BookSubInfo>
          <S.BookSubInfo variant="body2">{publisher}</S.BookSubInfo>
          <S.BookSubInfo variant="body2">{pubDate}</S.BookSubInfo>
        </S.BookSubInfoWrapper>
        <S.BookSubInfo variant="body2">{categoryName}</S.BookSubInfo>
        <S.BookDescription variant="body2">{description}</S.BookDescription>
      </S.BookInfo>
    </S.SearchBookListItem>
  );
}
