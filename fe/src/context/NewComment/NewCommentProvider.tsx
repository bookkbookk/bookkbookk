import { ReactNode, useMemo, useState } from "react";
import { NewCommentState } from "../type";
import {
  NewCommentActionsContext,
  NewCommentStateContext,
} from "./NewCommentContext";

export function NewCommentProvider({ children }: { children: ReactNode }) {
  const [commentState, setCommentState] = useState<NewCommentState>({
    content: "",
  });

  const bookmarkCommentActions = useMemo(
    () => ({
      setContent: (content: string) => {
        setCommentState((prev) => ({
          ...prev,
          content,
        }));
      },
      setPage: (page: number) => {
        setCommentState((prev) => ({
          ...prev,
          page,
        }));
      },
    }),
    []
  );

  return (
    <NewCommentStateContext.Provider value={commentState}>
      <NewCommentActionsContext.Provider value={bookmarkCommentActions}>
        {children}
      </NewCommentActionsContext.Provider>
    </NewCommentStateContext.Provider>
  );
}
