import { createContext } from "react";
import { BookmarkListAction, BookmarkListState } from "../type";

export const BookmarkListStateContext = createContext<
  BookmarkListState | undefined
>(undefined);

export const BookmarkListActionsContext = createContext<
  BookmarkListAction | undefined
>(undefined);
