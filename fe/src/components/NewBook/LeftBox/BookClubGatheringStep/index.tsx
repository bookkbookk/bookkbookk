import { usePostNewBook } from "@api/book/queries";
import { MESSAGE } from "@constant/index";
import { Box, Typography } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useBookChoice } from "store/newBook/useBookChoice";
import { useBookClubChoice } from "store/newBook/useBookClubChoice";
import Navigation from "../Navigation";
import { BookClubChoice } from "./BookClubChoice";

export default function BookClubGatheringStep({
  onPrev,
}: {
  onPrev: () => void;
}) {
  const [bookClubChoice, setBookClubChoice] = useBookClubChoice();
  const [bookChoice, setBookChoice] = useBookChoice();
  const isFilled = bookClubChoice && bookChoice;
  const navigate = useNavigate();

  const { onPostNewBook } = usePostNewBook({
    onSuccessCallback: (bookId: number) => {
      navigate(`${ROUTE_PATH.chapters}/${bookId}`);
      setBookClubChoice(null);
      setBookChoice(null);
    },
  });
  const onNext = () => {
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
      <Navigation {...{ onPrev, onNext }} />
      <BookClubChoice />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          gap: "1rem",
          width: "100%",
        }}>
        <Typography variant="h3">북클럽 모임을 생성해보세요!</Typography>
        {/* <GatheringTable /> */}
      </Box>
    </Box>
  );
}
