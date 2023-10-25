import { BookInfo } from "@api/book/type";
import { BookClubProfile } from "@api/bookClub/type";
import { NEW_BOOK_TABS } from "@components/constants";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

// TODO: atom 별로 newBook에 분리하기

type BookClubChoice = Pick<BookClubProfile, "id" | "name" | "profileImgUrl">;

type Gathering = {
  id: number;
  type: "ONLINE" | "OFFLINE";
  date: string;
  location: string;
};

type NewBookTabAtomAction =
  | {
      type: "PREV";
    }
  | {
      type: "NEXT";
    }
  | {
      type: "INDEX";
      payload: number;
    };

type GatheringAtomAction =
  | {
      type: "ADD";
    }
  | {
      type: "REMOVE";
      payload: number;
    };

const activeTabAtom = atom<number>(NEW_BOOK_TABS[0].id);
const bookChoiceAtom = atom<BookInfo | null>(null);
const bookClubChoiceAtom = atom<BookClubChoice | null>(null);
const gatheringAtom = atom<Gathering[]>([]);

const useGatheringAtom = atom(
  (get) => get(gatheringAtom),
  (_, set, action: GatheringAtomAction) => {
    switch (action.type) {
      case "ADD":
        set(gatheringAtom, (prev) => [
          ...prev,
          {
            id: prev.length + 1,
            type: "ONLINE",
            date: "",
            location: "",
          },
        ]);
        break;
      case "REMOVE":
        set(gatheringAtom, (prev) =>
          prev.filter((_, index) => index !== action.payload)
        );
        break;
    }
  }
);

const useBookChoiceAtom = atom(
  (get) => get(bookChoiceAtom),
  (_, set, payload: BookInfo | null) => {
    set(bookChoiceAtom, payload);
  }
);

const useActiveTabAtom = atom(
  (get) => get(activeTabAtom),
  (_, set, action: NewBookTabAtomAction) => {
    switch (action.type) {
      case "PREV":
        set(activeTabAtom, (prev) => prev - 1);
        break;
      case "NEXT":
        set(activeTabAtom, (prev) => prev + 1);
        break;
      case "INDEX":
        set(activeTabAtom, action.payload);
        break;
    }
  }
);

const useBookClubChoiceAtom = atom(
  (get) => get(bookClubChoiceAtom),
  (_, set, payload: BookClubChoice | null) => {
    set(bookClubChoiceAtom, payload);
  }
);

const useActiveTabValue = () => useAtomValue(useActiveTabAtom);
const useSetActiveTab = () => useSetAtom(useActiveTabAtom);
const useActiveTab = () => useAtom(useActiveTabAtom);

const useBookChoiceValue = () => useAtomValue(useBookChoiceAtom);
const useSetBookChoice = () => useSetAtom(useBookChoiceAtom);
const useBookChoice = () => useAtom(useBookChoiceAtom);

const useBookClubChoiceValue = () => useAtomValue(useBookClubChoiceAtom);
const useSetBookClubChoice = () => useSetAtom(useBookClubChoiceAtom);
const useBookClubChoice = () => useAtom(useBookClubChoiceAtom);

const useGatheringValue = () => useAtomValue(useGatheringAtom);
const useSetGathering = () => useSetAtom(useGatheringAtom);
const useGathering = () => useAtom(useGatheringAtom);

export {
  useActiveTab,
  useActiveTabValue,
  useBookChoice,
  useBookChoiceValue,
  useBookClubChoice,
  useBookClubChoiceValue,
  useGathering,
  useGatheringValue,
  useSetActiveTab,
  useSetBookChoice,
  useSetBookClubChoice,
  useSetGathering,
};
