import { Avatar, Box, Typography } from "@mui/material";
import { useBookClubValue } from "store/useBookClub";
import * as S from "./common.style";

export default function BookClubCard() {
  const bookClub = useBookClubValue();

  return (
    <S.Card>
      <Typography variant="h6">북클럽 정보</Typography>
      <Box sx={{ display: "flex", gap: "1rem", alignItems: "center" }}>
        <Avatar src={bookClub?.previewUrl} />
        <Typography variant="subtitle1">{bookClub?.name}</Typography>
      </Box>
    </S.Card>
  );
}
