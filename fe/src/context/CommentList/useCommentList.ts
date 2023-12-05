import { useContext } from "react";
import {
  CommentListActionsContext,
  CommentListStateContext,
} from "./CommentListContext";

export function useCommentListState() {
  const context = useContext(CommentListStateContext);

  if (context === undefined) {
    throw new Error(
      "useCommentListState must be used within a CommentListProvider"
    );
  }

  return context;
}

export function useCommentListActions() {
  const context = useContext(CommentListActionsContext);

  if (context === undefined) {
    throw new Error(
      "useCommentListActions must be used within a CommentListProvider"
    );
  }

  return context;
}
