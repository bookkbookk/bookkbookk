import { Dayjs } from "dayjs";

export type GatheringFormInfo = {
  id: string | null;
  dateTime: Dayjs | null;
  place: string;
};
