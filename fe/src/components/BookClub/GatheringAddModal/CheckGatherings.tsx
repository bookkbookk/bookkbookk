import { usePostNewGathering } from "@api/gatherings/queries";
import BookInfo from "@components/common/BookInfo";
import Navigation from "@components/common/Navigation";
import { Button, Stack, TableBody, TableRow, Typography } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useParams } from "react-router-dom";
import { useGathering } from "store/useGathering";
import { GatheringTable } from "./GatheringInfo/GatheringTable/GatheringTable";
import { GatheringTableItemCell } from "./GatheringInfo/GatheringTable/GatheringTableItemCell";

export default function CheckGatherings({
  onPrev,
  onNext,
}: {
  onPrev: () => void;
  onNext: () => void;
}) {
  const { bookClubId } = useParams<{ bookClubId: string }>();
  const [gatheringInfo, setGatheringInfo] = useGathering();
  const { onPostNewGathering } = usePostNewGathering({
    onPostNewGatheringSuccess: () => {
      onNext();
      setGatheringInfo({ type: "RESET" });
    },
  });

  const onCompleteButtonClick = () => {
    if (!bookClubId) throw new Error("bookClubId is undefined");
    if (!gatheringInfo.book) {
      return enqueueSnackbar("책을 선택해주세요", { variant: "error" });
    }
    if (gatheringInfo.gatherings.length === 0) {
      return enqueueSnackbar("모임을 추가해주세요", { variant: "error" });
    }

    onPostNewGathering({
      bookClubId: Number(bookClubId),
      gatheringInfo: {
        bookId: gatheringInfo.book.id,
        gatherings: gatheringInfo.gatherings.map((gathering) => ({
          dateTime: gathering.dateTime,
          place: gathering.place,
        })),
      },
    });
  };

  return (
    <Stack gap={3}>
      <Navigation onPrev={{ onClick: onPrev }} />
      <Typography variant="h5">
        생성할 모임 정보를 확인하고, 완료 버튼을 눌러주세요!
      </Typography>
      <Stack gap={2}>
        <Typography variant="h6">선택한 책</Typography>
        {gatheringInfo.book && <BookInfo book={gatheringInfo.book} />}
      </Stack>
      <Stack gap={2}>
        <Typography variant="h6">모임</Typography>
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
      </Stack>
      <Button
        variant="contained"
        color="primary"
        onClick={onCompleteButtonClick}>
        완료
      </Button>
    </Stack>
  );
}
