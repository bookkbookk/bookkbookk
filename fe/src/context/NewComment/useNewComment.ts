import { useContext } from "react";
import {
  NewCommentActionsContext,
  NewCommentStateContext,
} from "./NewCommentContext";

export function useNewCommentState() {
  const context = useContext(NewCommentStateContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkCommentState must be used within a BookmarkCommentProvider"
    );
  }

  return context;
}

export function useNewCommentActions() {
  const context = useContext(NewCommentActionsContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkCommentActions must be used within a BookmarkCommentProvider"
    );
  }

  return context;
}
