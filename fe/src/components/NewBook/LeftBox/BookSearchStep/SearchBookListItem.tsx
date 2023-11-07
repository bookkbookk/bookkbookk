import { BookInfo as BookInfoType } from "@api/book/type";
import * as S from "@components/NewBook/style";
import {
  BookCoverImage,
  BookDescription,
  ListItemButton,
} from "@components/common/common.style";
import { Typography } from "@mui/material";

export function SearchBookListItem(bookInfo: BookInfoType) {
  const { title, author, pubDate, description, cover, category, publisher } =
    bookInfo;

  return (
    <ListItemButton>
      <BookCoverImage
        src={cover}
        alt={title}
        sx={{
          minWidth: "90px",
          maxWidth: "90px",
          minHeight: "120px",
          maxHeight: "120px",
        }}
      />
      <S.InfoWrapper>
        <S.Title variant="h6">{title}</S.Title>
        <S.SubInfoWrapper>
          <Typography variant="body2">{author}</Typography>
          <Typography variant="body2">{publisher}</Typography>
          <Typography variant="body2">{pubDate}</Typography>
        </S.SubInfoWrapper>
        <Typography variant="body2">{category}</Typography>
        <BookDescription variant="body2">{description}</BookDescription>
      </S.InfoWrapper>
    </ListItemButton>
  );
}
