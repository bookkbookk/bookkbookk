import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

type BookClub = {
  name: string;
  previewUrl?: string;
  profileImage?: File;
  memberIDList?: number[]; // 참고: member id list
};

const bookClubAtom = atom<BookClub | null>(null);
const useBookClubAtom = atom(
  (get) => get(bookClubAtom),
  (_, set, payload: BookClub | null) => {
    set(bookClubAtom, payload);
  }
);

const useBookClubValue = () => useAtomValue(useBookClubAtom);
const useSetBookClub = () => useSetAtom(useBookClubAtom);
const useBookClub = () => useAtom(useBookClubAtom);

export { useBookClub, useBookClubValue, useSetBookClub };
