import { BookInfo } from "@api/book/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

const bookChoiceAtom = atom<BookInfo | null>(null);

const useBookChoiceAtom = atom(
  (get) => get(bookChoiceAtom),
  (_, set, payload: BookInfo | null) => {
    set(bookChoiceAtom, payload);
  }
);

const useBookChoiceValue = () => useAtomValue(useBookChoiceAtom);
const useSetBookChoice = () => useSetAtom(useBookChoiceAtom);
const useBookChoice = () => useAtom(useBookChoiceAtom);

export { useBookChoice, useBookChoiceValue, useSetBookChoice };
