import { fetcher, formDataConfig } from "@api/fetcher";
import { makeFormData } from "@api/utils";
import { stringify } from "qs";
import { BOOK_CLUB_API_PATH } from "../constants";
import { BookClubCreationInfo, BookClubProfile, NewBookClubInfo } from "./type";

export const postNewBookClub = async (bookClubInfo: BookClubCreationInfo) => {
  const bookClubInfoFormData = makeFormData(bookClubInfo);

  const { data } = await fetcher.post<NewBookClubInfo>(
    BOOK_CLUB_API_PATH.bookClubs,
    bookClubInfoFormData,
    formDataConfig
  );
  return data;
};

export const getBookClubList = async (option?: {
  status: "open" | "closed";
}) => {
  const pathVariable = stringify(option);

  const { data } = await fetcher.get<BookClubProfile[]>(
    `${BOOK_CLUB_API_PATH.bookClubs}${pathVariable && `?${pathVariable}`}`
  );
  return data;
};
