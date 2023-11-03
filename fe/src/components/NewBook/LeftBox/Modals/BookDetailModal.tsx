import { BookInfo } from "@api/book/type";
import * as S from "@components/NewBook/style";
import { ButtonWrapper, ModalBox } from "@components/common/common.style";
import LaunchIcon from "@mui/icons-material/Launch";
import { Button, Modal, Tooltip, Typography } from "@mui/material";
import { useSetBookChoice } from "store/newBook/useBookChoice";

export default function BookDetailModal({
  open,
  handleClose,
  bookInfo,
  onSelectBook,
}: {
  open: boolean;
  handleClose: () => void;
  bookInfo: BookInfo;
  onSelectBook: () => void;
}) {
  const {
    title,
    author,
    pubDate,
    description,
    cover,
    category,
    publisher,
    link,
  } = bookInfo;

  const setBookChoice = useSetBookChoice();

  const onSelectButtonClick = () => {
    setBookChoice(bookInfo);
    onSelectBook();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <ModalBox>
        <S.ImageWrapper>
          <S.BookCoverImage
            src={cover}
            alt={title}
            width={160}
            height={160}
            sx={{ objectFit: "contain" }}
          />
        </S.ImageWrapper>
        <Tooltip
          title="서점 페이지의 도서 정보로 이동해요!"
          placement="top"
          arrow>
          <S.BookLink to={link} target="blank">
            {title}
            <LaunchIcon fontSize="inherit" />
          </S.BookLink>
        </Tooltip>
        <S.BookSubInfo>
          <Typography variant="body2">{author}</Typography>
          <S.SubInfoWrapper>
            <Typography variant="body2">{publisher}</Typography>
            <Typography variant="body2">{pubDate}</Typography>
          </S.SubInfoWrapper>
          <Typography variant="body2">{category}</Typography>
        </S.BookSubInfo>
        <Typography variant="body2">{description}</Typography>
        <ButtonWrapper>
          <Button variant="outlined" onClick={handleClose}>
            취소하기
          </Button>
          <Button variant="contained" onClick={onSelectButtonClick}>
            선택하기
          </Button>
        </ButtonWrapper>
      </ModalBox>
    </Modal>
  );
}
