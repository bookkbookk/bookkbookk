import { BookListItem } from "@api/book/type";
import { fetcher, formDataConfig } from "@api/fetcher";
import { makeFormData } from "@api/utils";
import { send } from "@emailjs/browser";
import { BOOK_CLUB_API_PATH } from "../constants";
import {
  BookClubCreationInfo,
  BookClubProfile,
  ClosedBookClubDetail,
  EmailSubmitInfo,
  NewBookClubInfo,
  OpenBookClubDetail,
} from "./type";
const {
  VITE_EMAIL_JS_PUBLIC_KEY,
  VITE_EMAIL_JS_SERVICE_ID,
  VITE_EMAIL_JS_TEMPLATE_ID,
} = import.meta.env;

export const postNewBookClub = async (bookClubInfo: BookClubCreationInfo) => {
  const bookClubInfoFormData = makeFormData(bookClubInfo);

  const { data } = await fetcher.post<NewBookClubInfo>(
    BOOK_CLUB_API_PATH.bookClubs,
    bookClubInfoFormData,
    formDataConfig
  );
  return data;
};

export const getBookClubDetail = async (bookClubId: number) => {
  const { data } = await fetcher.get<ClosedBookClubDetail | OpenBookClubDetail>(
    `${BOOK_CLUB_API_PATH.bookClubs}/${bookClubId}`
  );
  return data;
};

export const getBookClubList = async (option?: {
  status: "OPEN" | "CLOSED";
}) => {
  const { data } = await fetcher.get<BookClubProfile[]>(
    BOOK_CLUB_API_PATH.bookClubs,
    {
      params: option,
    }
  );
  return data;
};

export const sendEmail = async (emailSubmitInfo: EmailSubmitInfo) => {
  return await send(
    VITE_EMAIL_JS_SERVICE_ID,
    VITE_EMAIL_JS_TEMPLATE_ID,
    emailSubmitInfo,
    VITE_EMAIL_JS_PUBLIC_KEY
  );
};

export const postJoinBookClub = async (invitationCode: string) => {
  const { data } = await fetcher.post<{ bookClubId: number }>(
    BOOK_CLUB_API_PATH.join,
    { invitationCode }
  );

  return data;
};

export const getBookClubBooks = async ({
  bookClubId,
  cursor,
  size,
}: {
  bookClubId: number;
  cursor: number;
  size: number;
}) => {
  const { data } = await fetcher.get<{
    books: Omit<BookListItem, "bookClub">;
    hasNext: boolean;
  }>(`${BOOK_CLUB_API_PATH.bookClubs}/${bookClubId}/books`, {
    params: { cursor, size },
  });
  return data;
};

// TODO: 테스트 코드로 옮기기
// export const mockSendEmail = async (emailSubmitInfo: EmailSubmitInfo) => {
//   throw new Error("Not implemented");
// };
