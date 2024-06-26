import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";
import { Info, ReducerAction } from "./type";

type ChapterListAtomActionMap = {
  UPDATE_CHAPTER: { chapterIndex: number; info: Info };
  DELETE_CHAPTER: { chapterIndex: number };
};

type ChaptersAtomAction =
  | { type: "ADD_CHAPTER" }
  | { type: "RESET_CHAPTER" }
  | ReducerAction<ChapterListAtomActionMap>;

const chapterListAtom = atom<Info[]>([{ title: "" }]);

const useChapterListAtom = atom(
  (get) => get(chapterListAtom),
  (_, set, action: ChaptersAtomAction) => {
    switch (action.type) {
      case "ADD_CHAPTER":
        set(chapterListAtom, (prev) => [...prev, { title: "" }]);
        break;
      case "UPDATE_CHAPTER":
        set(chapterListAtom, (prev) =>
          prev.map((chapter, index) =>
            index === action.payload.chapterIndex
              ? { ...chapter, ...action.payload.info }
              : chapter
          )
        );
        break;
      case "DELETE_CHAPTER":
        set(chapterListAtom, (prev) =>
          prev.filter((_, index) => index !== action.payload.chapterIndex)
        );
        break;
      case "RESET_CHAPTER":
        set(chapterListAtom, [{ title: "" }]);
        break;
    }
  }
);

const useChapterListValue = () => useAtomValue(useChapterListAtom);
const useSetChapterList = () => useSetAtom(useChapterListAtom);
const useChapterList = () => useAtom(useChapterListAtom);

export { useChapterList, useChapterListValue, useSetChapterList };
