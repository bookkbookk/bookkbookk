import { useContext } from "react";
import {
  BookmarkActionsContext,
  BookmarkStateContext,
} from "./BookmarkContext";

export function useBookmarkState() {
  const context = useContext(BookmarkStateContext);

  if (context === undefined) {
    throw new Error(
      "useNewBookmarkValue must be used within a NewBookmarkProvider"
    );
  }

  return context;
}

export function useNewBookmarkActions() {
  const context = useContext(BookmarkActionsContext);

  if (context === undefined) {
    throw new Error(
      "useNewBookmarkActions must be used within a NewBookmarkProvider"
    );
  }

  return context;
}
