import { useContext } from "react";
import {
  BookmarkCommentActionsContext,
  BookmarkCommentStateContext,
} from "./BookmarkCommentContext";

export function useBookmarkCommentState() {
  const context = useContext(BookmarkCommentStateContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkCommentState must be used within a BookmarkCommentProvider"
    );
  }

  return context;
}

export function useBookmarkCommentActions() {
  const context = useContext(BookmarkCommentActionsContext);

  if (context === undefined) {
    throw new Error(
      "useBookmarkCommentActions must be used within a BookmarkCommentProvider"
    );
  }

  return context;
}
