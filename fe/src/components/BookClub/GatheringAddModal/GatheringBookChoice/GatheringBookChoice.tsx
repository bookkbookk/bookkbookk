import { useGetBookClubBooks } from "@api/bookClub/queries";
import Navigation from "@components/common/Navigation";
import { Target } from "@components/common/common.style";
import { useIntersectionObserver } from "@hooks/useIntersectionObserver";
import { CircularProgress, List, Stack, Typography } from "@mui/material";
import { useParams } from "react-router-dom";
import { BookClubBookItem } from "./BookClubBookItem";

export function GatheringBookChoice({ onNext }: { onNext: () => void }) {
  const { bookClubId } = useParams<{ bookClubId: string }>();
  const { bookClubBooks, hasNext, isFetching, fetchNextBooks } =
    useGetBookClubBooks(Number(bookClubId));

  const targetRef = useIntersectionObserver(
    hasNext && !isFetching,
    fetchNextBooks
  );

  return (
    <Stack>
      <Navigation onNext={{ onClick: onNext }} />
      <Typography variant="h6">모임에서 함께 읽을 책을 선택해주세요</Typography>
      {bookClubBooks && (
        <List
          sx={{
            width: "100%",
            maxHeight: "36rem",
            overflow: "scroll",
          }}>
          {bookClubBooks.map((book) => (
            <BookClubBookItem key={book.id} book={book} onClick={onNext} />
          ))}
          {isFetching ? <CircularProgress /> : <Target ref={targetRef} />}
        </List>
      )}
    </Stack>
  );
}
