import { createContext } from "react";
import { NewCommentAction, NewCommentState } from "../type";

export const NewCommentStateContext = createContext<
  NewCommentState | undefined
>(undefined);

export const NewCommentActionsContext = createContext<
  NewCommentAction | undefined
>(undefined);
