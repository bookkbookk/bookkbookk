import { createContext } from "react";
import { BookmarkAction, BookmarkState } from "../type";

export const BookmarkStateContext = createContext<BookmarkState | undefined>(
  undefined
);

export const BookmarkActionsContext = createContext<BookmarkAction | undefined>(
  undefined
);
