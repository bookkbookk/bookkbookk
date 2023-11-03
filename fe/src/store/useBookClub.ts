import { NewBookClubInfo } from "@api/bookClub/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";
import { enqueueSnackbar } from "notistack";
import { ReducerAction } from "./type";

type BookClubAtomActionMap = {
  UPDATE: Partial<BookClub>;
};

type BookClubAtomAction =
  | { type: "RESET" }
  | ReducerAction<BookClubAtomActionMap>;

type BookClubMemberEmailsAtomActionMap = {
  ADD_EMAIL: string;
  DELETE_EMAIL: string;
};

type BookClubMemberEmailsAtomAction =
  | { type: "RESET" }
  | ReducerAction<BookClubMemberEmailsAtomActionMap>;

type BookClubInfo = {
  name: string;
  previewUrl?: string;
  profileImage?: File;
};

type BookClub = BookClubInfo & Partial<NewBookClubInfo>;

const bookClubAtom = atom<BookClub | null>(null);
const bookClubMembersAtom = atom<string[]>([]);

const useBookClubAtom = atom(
  (get) => get(bookClubAtom),
  (_, set, action: BookClubAtomAction) => {
    switch (action.type) {
      case "UPDATE":
        set(bookClubAtom, (prev) => {
          if (!prev) {
            return { ...action.payload };
          }
          return { ...prev, ...action.payload };
        });
        break;
      case "RESET":
        set(bookClubAtom, null);
        break;
    }
  }
);

const useBookClubMemberEmailsAtom = atom(
  (get) => get(bookClubMembersAtom),
  (_, set, action: BookClubMemberEmailsAtomAction) => {
    switch (action.type) {
      case "ADD_EMAIL":
        set(bookClubMembersAtom, (prev) => {
          if (prev.includes(action.payload)) {
            enqueueSnackbar("이미 추가된 이메일입니다.", { variant: "error" });
            return prev;
          }
          return [action.payload, ...prev];
        });
        break;
      case "DELETE_EMAIL":
        set(bookClubMembersAtom, (prev) => {
          return prev.filter((email) => email !== action.payload);
        });
        break;
      case "RESET":
        set(bookClubMembersAtom, []);
        break;
    }
  }
);

const useBookClubValue = () => useAtomValue(useBookClubAtom);
const useSetBookClub = () => useSetAtom(useBookClubAtom);
const useBookClub = () => useAtom(useBookClubAtom);

const useBookClubMemberEmailsValue = () =>
  useAtomValue(useBookClubMemberEmailsAtom);
const useSetBookClubMemberEmails = () =>
  useSetAtom(useBookClubMemberEmailsAtom);
const useBookClubMemberEmails = () => useAtom(useBookClubMemberEmailsAtom);

export {
  useBookClub,
  useBookClubMemberEmails,
  useBookClubMemberEmailsValue,
  useBookClubValue,
  useSetBookClub,
  useSetBookClubMemberEmails,
};
