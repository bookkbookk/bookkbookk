import { usePostNewBook } from "@api/book/queries";
import NewBookTabs from "@components/NewBook/LeftBox/NewBookTabs";
import * as S from "@components/common/common.style";
import BackupIcon from "@mui/icons-material/Backup";
import { Button, Tooltip } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useBookChoiceValue, useBookClubChoiceValue } from "store/useNewBook";
import ChaptersPanel from "./ChaptersPanel";
import GatheringPanel from "./GatheringPanel/GatheringPanel";
import SearchPanel from "./SearchPanel";

export default function NewBookLeftBox() {
  const bookClubChoice = useBookClubChoiceValue();
  const bookChoice = useBookChoiceValue();
  const isEssentialInfoFilled = bookClubChoice && bookChoice;
  const navigate = useNavigate();

  const { onPostNewBookClub } = usePostNewBook({
    onSuccessCallback: (bookId: number) => {
      navigate(`${ROUTE_PATH.bookDetail}/${bookId}`);
    },
  });
  const onClickPostNewBook = () => {
    if (!bookClubChoice || !bookChoice) return;

    onPostNewBookClub({
      bookClubId: bookClubChoice.id,
      isbn: bookChoice.isbn,
      title: bookChoice.title,
      cover: bookChoice.cover,
      author: bookChoice.author,
      category: bookChoice.category,
    });
  };

  return (
    <S.LeftBox
      sx={{
        alignItems: "flex-start",
        justifyContent: "flex-start",
        padding: "1rem 2rem",
      }}>
      <S.BoxHeader>
        <NewBookTabs />
        <Tooltip
          title="추가할 책과 북클럽은 반드시 선택해야 해요!"
          placement="top-start"
          arrow>
          <div>
            <Button
              variant="contained"
              endIcon={<BackupIcon />}
              onClick={onClickPostNewBook}
              disabled={!isEssentialInfoFilled}>
              책 추가 완료하기
            </Button>
          </div>
        </Tooltip>
      </S.BoxHeader>
      <SearchPanel index={0} />
      <GatheringPanel index={1} />
      <ChaptersPanel index={2} />
    </S.LeftBox>
  );
}
