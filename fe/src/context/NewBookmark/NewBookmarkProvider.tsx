import { ReactNode, useMemo, useState } from "react";
import { NewBookmarkState } from "../type";
import {
  NewBookmarkActionsContext,
  NewBookmarkStateContext,
} from "./NewBookmarkContext";

export function NewBookmarkProvider({ children }: { children: ReactNode }) {
  const [bookmarkState, setBookmarkState] = useState<NewBookmarkState>({
    content: "",
  });

  const bookmarkActions = useMemo(
    () => ({
      setContent: (content: string) => {
        setBookmarkState((prev) => ({
          ...prev,
          content,
        }));
      },
      setPage: (page: string) => {
        setBookmarkState((prev) => ({
          ...prev,
          page,
        }));
      },
    }),
    []
  );

  return (
    <NewBookmarkStateContext.Provider value={bookmarkState}>
      <NewBookmarkActionsContext.Provider value={bookmarkActions}>
        {children}
      </NewBookmarkActionsContext.Provider>
    </NewBookmarkStateContext.Provider>
  );
}
