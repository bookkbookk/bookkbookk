import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

// TODO: 어떤 데이터 구조로 관리하는게 좋을지 고민해보기

export type Info = {
  title: string;
  part?: string;
};

export type Chapter = Info & { topics: Info[] };

type ChaptersAtomActionMap = {
  UPDATE_CHAPTER: { chapterIndex: number; info: Info };
  DELETE_CHAPTER: { chapterIndex: number };
  ADD_TOPIC: { chapterIndex: number };
  DELETE_TOPIC: { chapterIndex: number; topicIndex: number };
  UPDATE_TOPIC: { chapterIndex: number; topicIndex: number; info: Info };
};

type ChaptersAtomAction =
  | { type: "ADD_CHAPTER" }
  | {
      [K in keyof ChaptersAtomActionMap]: {
        type: K;
        payload: ChaptersAtomActionMap[K];
      };
    }[keyof ChaptersAtomActionMap];

const chapterListAtom = atom<Chapter[]>([]);

const useChapterListAtom = atom(
  (get) => get(chapterListAtom),
  (_, set, action: ChaptersAtomAction) => {
    switch (action.type) {
      case "ADD_CHAPTER":
        set(chapterListAtom, (prev) => [
          ...prev,
          {
            title: "",
            topics: [],
          },
        ]);
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
      case "ADD_TOPIC":
        set(chapterListAtom, (prev) => {
          return prev.map((chapter, index) =>
            index === action.payload.chapterIndex
              ? { ...chapter, topics: [...chapter.topics, { title: "" }] }
              : chapter
          );
        });
        break;
      case "DELETE_TOPIC":
        set(chapterListAtom, (prev) =>
          prev.map((chapter, index) =>
            index === action.payload.chapterIndex
              ? {
                  ...chapter,
                  topics: chapter.topics.filter(
                    (_, index) => index !== action.payload.topicIndex
                  ),
                }
              : chapter
          )
        );
        break;
      case "UPDATE_TOPIC":
        break;
    }
  }
);

const useChapterListValue = () => useAtomValue(useChapterListAtom);
const useSetChapterList = () => useSetAtom(useChapterListAtom);
const useChapterList = () => useAtom(useChapterListAtom);

export { useChapterList, useChapterListValue, useSetChapterList };
