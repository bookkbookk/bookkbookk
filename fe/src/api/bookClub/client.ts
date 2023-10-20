import { fetcher, formDataConfig } from "@api/fetcher";
import { makeFormData } from "@api/utils";
import { BOOK_CLUB_API_PATH } from "./constants";
import { BookClubInfo } from "./type";

export const postNewBookClub = async (bookClubInfo: BookClubInfo) => {
  const bookClubInfoFormData = makeFormData(bookClubInfo);

  const { data } = await fetcher.post<{ bookClubId: number }>(
    BOOK_CLUB_API_PATH.bookClubs,
    bookClubInfoFormData,
    formDataConfig
  );
  return data;
};
