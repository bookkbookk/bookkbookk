import { createContext } from "react";
import { NewBookmarkAction, NewBookmarkState } from "../type";

export const NewBookmarkStateContext = createContext<
  NewBookmarkState | undefined
>(undefined);

export const NewBookmarkActionsContext = createContext<
  NewBookmarkAction | undefined
>(undefined);
