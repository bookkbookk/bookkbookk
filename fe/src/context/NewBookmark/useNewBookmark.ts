import { useContext } from "react";
import {
  NewBookmarkActionsContext,
  NewBookmarkStateContext,
} from "./NewBookmarkContext";

export function useNewBookmarkState() {
  const context = useContext(NewBookmarkStateContext);

  if (context === undefined) {
    throw new Error(
      "useNewBookmarkValue must be used within a NewBookmarkProvider"
    );
  }

  return context;
}

export function useNewBookmarkActions() {
  const context = useContext(NewBookmarkActionsContext);

  if (context === undefined) {
    throw new Error(
      "useNewBookmarkActions must be used within a NewBookmarkProvider"
    );
  }

  return context;
}
