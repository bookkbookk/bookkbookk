import { usePostNewBook } from "@api/book/queries";
import { usePostNewGathering } from "@api/gatherings/queries";
import GatheringInfo from "@components/BookClub/GatheringAddModal/GatheringInfo/GatheringInfo";
import Navigation from "@components/common/Navigation";
import { MESSAGE } from "@constant/index";
import { Box, Typography } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useBookChoice } from "store/newBook/useBookChoice";
import { useBookClubChoice } from "store/newBook/useBookClubChoice";
import { useGathering } from "store/useGathering";
import { BookClubChoice } from "./BookClubChoice";

export default function BookClubGatheringStep({
  onPrev,
}: {
  onPrev: () => void;
}) {
  const navigate = useNavigate();

  const [bookClubChoice, setBookClubChoice] = useBookClubChoice();
  const [bookChoice, setBookChoice] = useBookChoice();
  const [gatheringInfo, setGatheringInfo] = useGathering();

  const { onPostNewBook } = usePostNewBook({
    onSuccessCallback: (bookId: number) => {
      if (!bookClubChoice) {
        throw new Error("bookClubChoice is undefined");
      }

      onPostNewGathering({
        bookClubId: bookClubChoice.id,
        gatheringInfo: {
          bookId,
          gatherings: gatheringInfo.gatherings.map((gathering) => ({
            dateTime: gathering.dateTime,
            place: gathering.place,
          })),
        },
      });
    },
  });

  const { onPostNewGathering } = usePostNewGathering({
    callback: (bookId: number) => {
      navigate(`${ROUTE_PATH.bookDetail}/${bookId}`, {
        replace: true,
        state: {
          book: { ...bookChoice, bookClub: bookClubChoice },
          isChapterAddMode: true,
        },
      });
      setBookClubChoice(null);
      setBookChoice(null);
      setGatheringInfo({ type: "RESET" });
    },
  });

  const onNext = () => {
    const isFilled = bookClubChoice && bookChoice;

    if (!isFilled) {
      return enqueueSnackbar(MESSAGE.NEW_BOOK_ALERT, { variant: "error" });
    }

    onPostNewBook({
      bookClubId: bookClubChoice.id,
      isbn: bookChoice.isbn,
      title: bookChoice.title,
      cover: bookChoice.cover,
      author: bookChoice.author,
      category: bookChoice.category,
    });
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        padding: "2rem 0",
        gap: "3rem",
        width: "100%",
      }}>
      <Navigation
        onPrev={{ onClick: onPrev, text: "이전 단계" }}
        onNext={{ onClick: onNext, text: "다음 단계" }}
      />
      <BookClubChoice />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          gap: "1rem",
          width: "100%",
        }}>
        <Typography variant="h3">북클럽 모임을 생성해보세요!</Typography>
        <GatheringInfo />
      </Box>
    </Box>
  );
}
