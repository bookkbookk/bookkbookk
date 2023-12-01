import { ReactNode, useMemo, useState } from "react";
import { NewBookmarkState } from "../type";
import {
  NewBookmarkActionsContext,
  NewBookmarkStateContext,
} from "./NewBookmarkContext";

export function NewBookmarkProvider({ children }: { children: ReactNode }) {
  const [newBookmarkState, setNewBookmarkState] = useState<NewBookmarkState>({
    content: "",
  });

  const newBookmarkActions = useMemo(
    () => ({
      setContent: (content: string) => {
        setNewBookmarkState((prev) => ({
          ...prev,
          content,
        }));
      },
      setPage: (page: string) => {
        setNewBookmarkState((prev) => ({
          ...prev,
          page,
        }));
      },
    }),
    []
  );

  return (
    <NewBookmarkStateContext.Provider value={newBookmarkState}>
      <NewBookmarkActionsContext.Provider value={newBookmarkActions}>
        {children}
      </NewBookmarkActionsContext.Provider>
    </NewBookmarkStateContext.Provider>
  );
}
