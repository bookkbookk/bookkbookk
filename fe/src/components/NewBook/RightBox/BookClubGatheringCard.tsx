import { Card, CardContent } from "@components/common/common.style";
import { Avatar, Typography } from "@mui/material";
import { useBookClubChoiceValue } from "store/newBook/useBookClubChoice";

export default function BookClubGatheringCard() {
  const bookClubChoice = useBookClubChoiceValue();

  return (
    <Card>
      <Typography variant="h6">북클럽 모임 정보</Typography>
      {bookClubChoice && (
        <CardContent>
          <Avatar
            src={bookClubChoice.profileImgUrl}
            alt={bookClubChoice.name}
          />
          <Typography variant="body1">{bookClubChoice.name}</Typography>
        </CardContent>
      )}
    </Card>
  );
}
