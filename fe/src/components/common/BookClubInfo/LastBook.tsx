import { BookClubInfo as BookClubInfoType } from "@api/bookClub/type";
import { Stack, Typography } from "@mui/material";

export default function LastBook({
  bookClubStatus,
  lastBook,
}: {
  bookClubStatus: "open" | "closed";
  lastBook: BookClubInfoType["lastBook"];
}) {
  const isOpenBookClub = bookClubStatus === "open";
  const lastBookTitle = isOpenBookClub ? "읽고 있는 책" : "마지막으로 읽은 책";

  return (
    <Stack gap={1} display="flex" flexDirection="row">
      <Typography variant="body2" sx={{ fontWeight: 700 }}>
        {lastBookTitle}
      </Typography>
      <Typography variant="body2">
        {`${lastBook.name} (${lastBook.author})`}
      </Typography>
    </Stack>
  );
}
