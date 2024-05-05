import { useContext } from "react";
import {
  BookmarkListActionsContext,
  BookmarkListStateContext,
} from "./BookmarkListContext";

export function useBookmarkListState() {
  const context = useContext(BookmarkListStateContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkListState must be used within a BookmarkListProvider"
    );
  }

  return context;
}

export function useBookmarkListActions() {
  const context = useContext(BookmarkListActionsContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkListActions must be used within a BookmarkListProvider"
    );
  }

  return context;
}
