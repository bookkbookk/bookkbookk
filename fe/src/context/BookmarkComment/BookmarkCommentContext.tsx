import { createContext } from "react";
import { BookmarkCommentAction, BookmarkCommentState } from "../type";

export const BookmarkCommentStateContext = createContext<
  BookmarkCommentState | undefined
>(undefined);

export const BookmarkCommentActionsContext = createContext<
  BookmarkCommentAction | undefined
>(undefined);
