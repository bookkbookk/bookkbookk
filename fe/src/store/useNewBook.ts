import { NEW_BOOK_TABS } from "@components/constants";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

type BookInfo = {
  isbn: string;
  title: string;
  cover: string;
  author: string;
  category: string;
  link: string;
};

type Gathering = {
  type: "ONLINE" | "OFFLINE";
  date: string;
  location: string;
};

type Chapter = {
  title: string;
  scope?: string;
  topics?: { title: string; scope?: string }[];
};

type NewBookAtomAction =
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

const bookInfoAtom = atom<BookInfo | null>(null);
const bookClubIdAtom = atom<number | null>(null);
const activeTabAtom = atom<number>(NEW_BOOK_TABS[0].id);
const gatheringAtom = atom<Gathering[]>([]);
const chapterAtom = atom<Chapter[]>([]);

const useBookInfoAtom = atom(
  (get) => get(bookInfoAtom),
  (_, set, payload: BookInfo | null) => {
    set(bookInfoAtom, payload);
  }
);

const useActiveTabAtom = atom(
  (get) => get(activeTabAtom),
  (_, set, action: NewBookAtomAction) => {
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

const useActiveTabValue = () => useAtomValue(useActiveTabAtom);
const useSetActiveTab = () => useSetAtom(useActiveTabAtom);
const useActiveTab = () => useAtom(useActiveTabAtom);

const useBookInfoValue = () => useAtomValue(useBookInfoAtom);
const useSetBookInfo = () => useSetAtom(useBookInfoAtom);
const useBookInfo = () => useAtom(useBookInfoAtom);

export {
  useActiveTab,
  useActiveTabValue,
  useBookInfo,
  useBookInfoValue,
  useSetActiveTab,
  useSetBookInfo,
};
