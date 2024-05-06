import { fetcher } from "@api/fetcher";
import { BOOK_CLUB_API_PATH} from "../constants";
import { NewBookClubGathering } from "./type";

export const postNewGathering = async ({
  bookClubId,
  gatheringInfo,
}: {
  bookClubId: number;
  gatheringInfo: NewBookClubGathering;
}) => {
  const { data } = await fetcher.post(
    `${BOOK_CLUB_API_PATH.gatherings(bookClubId)}`,
    gatheringInfo
  );

  return data;
};
