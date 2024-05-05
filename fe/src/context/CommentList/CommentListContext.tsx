import { createContext } from "react";
import { CommentListAction, CommentListState } from "../type";

export const CommentListStateContext = createContext<
  CommentListState | undefined
>(undefined);

export const CommentListActionsContext = createContext<
  CommentListAction | undefined
>(undefined);
