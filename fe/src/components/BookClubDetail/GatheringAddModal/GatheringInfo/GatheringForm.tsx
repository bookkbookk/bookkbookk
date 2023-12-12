import { Button, Stack, TextField } from "@mui/material";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import dayjs, { Dayjs } from "dayjs";
import { enqueueSnackbar } from "notistack";
import { useSetGathering } from "store/useGathering";
import { GatheringFormInfo } from "./type";

export default function GatheringForm({
  isEditGathering,
  gatheringInfo,
  setGatheringDate,
  setGatheringPlace,
  changeToNewGathering,
}: {
  isEditGathering: boolean;
  gatheringInfo: GatheringFormInfo;
  setGatheringDate: (dateTime: Dayjs | null) => void;
  setGatheringPlace: (place: string) => void;
  changeToNewGathering: () => void;
}) {
  const { dateTime, place } = gatheringInfo;

  const setGathering = useSetGathering();

  const addNewGathering = () => {
    if (!dateTime || !place) {
      return enqueueSnackbar("일시와 장소를 모두 입력해주세요", {
        variant: "error",
      });
    }
    setGathering({
      type: "ADD_GATHERING",
      payload: {
        dateTime: dateTime.toISOString(),
        place,
      },
    });
    setGatheringDate(dayjs(Date.now()));
    setGatheringPlace("");
  };

  const editGathering = () => {
    if (!dateTime || !place) {
      return enqueueSnackbar("일시와 장소를 모두 입력해주세요", {
        variant: "error",
      });
    }

    gatheringInfo.id &&
      setGathering({
        type: "EDIT_GATHERING",
        payload: {
          gatheringId: gatheringInfo.id,
          info: {
            dateTime: dateTime.toISOString(),
            place,
          },
        },
      });

    changeToNewGathering();
  };

  return (
    <Stack display={"flex"} flexDirection={"row"} gap={1}>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DateTimePicker
          label="일시"
          format="YYYY/MM/DD hh:mm a"
          value={dateTime}
          onChange={(newValue) => setGatheringDate(newValue)}
        />
      </LocalizationProvider>
      <TextField
        id="gathering-place"
        label="장소"
        value={place}
        onChange={(e) => setGatheringPlace(e.target.value)}
      />
      <Button
        aria-label="add-gathering"
        size="small"
        sx={{ minWidth: 80 }}
        variant="contained"
        onClick={isEditGathering ? editGathering : addNewGathering}>
        {isEditGathering ? "수정 완료" : "모임 추가"}
      </Button>
    </Stack>
  );
}
