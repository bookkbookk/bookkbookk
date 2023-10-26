import { Card, CardContent } from "@components/common/common.style";
import { Avatar, Typography } from "@mui/material";
import { useBookClubValue } from "store/useBookClub";

export default function BookClubCard() {
  const bookClub = useBookClubValue();

  return (
    <Card>
      <Typography variant="h6">북클럽 정보</Typography>
      <CardContent>
        {bookClub && (
          <>
            <Avatar src={bookClub.previewUrl} />
            <Typography variant="subtitle1">{bookClub.name}</Typography>
          </>
        )}
      </CardContent>
    </Card>
  );
}
