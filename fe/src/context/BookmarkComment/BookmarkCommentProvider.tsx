import { ReactNode, useMemo, useState } from "react";
import { BookmarkCommentState } from "../type";
import {
  BookmarkCommentActionsContext,
  BookmarkCommentStateContext,
} from "./BookmarkCommentContext";

export function BookmarkCommentProvider({ children }: { children: ReactNode }) {
  const [commentState, setCommentState] = useState<BookmarkCommentState>({
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
    <BookmarkCommentStateContext.Provider value={commentState}>
      <BookmarkCommentActionsContext.Provider value={bookmarkCommentActions}>
        {children}
      </BookmarkCommentActionsContext.Provider>
    </BookmarkCommentStateContext.Provider>
  );
}
