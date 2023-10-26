import { BookClubProfile } from "@api/bookClub/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

type BookClubChoice = Pick<BookClubProfile, "id" | "name" | "profileImgUrl">;

const bookClubChoiceAtom = atom<BookClubChoice | null>(null);

const useBookClubChoiceAtom = atom(
  (get) => get(bookClubChoiceAtom),
  (_, set, payload: BookClubChoice | null) => {
    set(bookClubChoiceAtom, payload);
  }
);

const useBookClubChoiceValue = () => useAtomValue(useBookClubChoiceAtom);
const useSetBookClubChoice = () => useSetAtom(useBookClubChoiceAtom);
const useBookClubChoice = () => useAtom(useBookClubChoiceAtom);

export { useBookClubChoice, useBookClubChoiceValue, useSetBookClubChoice };
