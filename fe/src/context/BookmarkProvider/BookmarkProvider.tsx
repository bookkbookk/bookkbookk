import { ReactNode, useMemo, useState } from "react";
import { BookmarkState } from "../type";
import {
  BookmarkActionsContext,
  BookmarkStateContext,
} from "./BookmarkContext";

export function BookmarkProvider({
  children,
  bookmark,
}: {
  children: ReactNode;
  bookmark?: BookmarkState;
}) {
  const [bookmarkState, setBookmarkState] = useState<BookmarkState>({
    content: bookmark?.content ?? "",
    page: bookmark?.page ?? "",
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
    <BookmarkStateContext.Provider value={bookmarkState}>
      <BookmarkActionsContext.Provider value={bookmarkActions}>
        {children}
      </BookmarkActionsContext.Provider>
    </BookmarkStateContext.Provider>
  );
}
