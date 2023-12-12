import Navigation from "@components/common/Navigation";
import { Stack, Typography } from "@mui/material";
import dayjs, { Dayjs } from "dayjs";
import { useState } from "react";
import { Gathering } from "store/useGathering";
import GatheringForm from "./GatheringForm";
import GatheringEditTableBody from "./GatheringTable/GatheringEditTableBody";
import { GatheringTable } from "./GatheringTable/GatheringTable";
import { GatheringFormInfo } from "./type";

export default function GatheringInfo({
  onPrev,
  onNext,
}: {
  onPrev?: () => void;
  onNext?: () => void;
}) {
  const [isEditGathering, setIsEditGathering] = useState<boolean>(false);
  const [gatheringInfo, setGatheringInfo] = useState<GatheringFormInfo>({
    id: null,
    dateTime: dayjs(Date.now()),
    place: "",
  });

  const onClickGatheringItem = (gathering: Gathering) => {
    const { id, dateTime, place } = gathering;

    setIsEditGathering(true);
    setGatheringInfo({
      id,
      place,
      dateTime: dayjs(dateTime),
    });
  };

  const setGatheringDate = (dateTime: Dayjs | null) => {
    setGatheringInfo({
      ...gatheringInfo,
      dateTime,
    });
  };

  const setGatheringPlace = (place: string) => {
    setGatheringInfo({
      ...gatheringInfo,
      place,
    });
  };

  const changeToNewGathering = () => {
    setIsEditGathering(false);
    setGatheringInfo({
      id: null,
      dateTime: dayjs(Date.now()),
      place: "",
    });
  };

  return (
    <Stack gap={2}>
      {onPrev && onNext && (
        <Navigation onPrev={{ onClick: onPrev }} onNext={{ onClick: onNext }} />
      )}
      <Typography variant="h6">모임 일정을 추가해주세요</Typography>
      <GatheringForm
        {...{
          isEditGathering,
          gatheringInfo,
          setGatheringDate,
          setGatheringPlace,
          changeToNewGathering,
        }}
      />
      <GatheringTable
        tableBody={<GatheringEditTableBody {...{ onClickGatheringItem }} />}
      />
    </Stack>
  );
}
