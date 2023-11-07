import { GatheringTable } from "@components/BookClub/GatheringAddModal/GatheringInfo/GatheringTable/GatheringTable";
import { GatheringTableItemCell } from "@components/BookClub/GatheringAddModal/GatheringInfo/GatheringTable/GatheringTableItemCell";
import { Card, CardContent } from "@components/common/common.style";
import { Avatar, TableBody, TableRow, Typography } from "@mui/material";
import { useBookClubChoiceValue } from "store/newBook/useBookClubChoice";
import { useGatheringValue } from "store/useGathering";

export default function BookClubGatheringCard() {
  const bookClubChoice = useBookClubChoiceValue();
  const gatheringInfo = useGatheringValue();

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
      <GatheringTable
        tableBody={
          <TableBody>
            {gatheringInfo.gatherings.map((gathering, index) => (
              <TableRow key={gathering.id}>
                <GatheringTableItemCell
                  index={index}
                  dateTime={gathering.dateTime}
                  place={gathering.place}
                />
              </TableRow>
            ))}
          </TableBody>
        }
      />
    </Card>
  );
}
