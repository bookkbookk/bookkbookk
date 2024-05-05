import { Bookmark } from "@api/bookmarks/type";
import { ReactNode, useMemo, useState } from "react";
import {
  BookmarkListActionsContext,
  BookmarkListStateContext,
} from "./BookmarkListContext";

export function BookmarkListProvider({
  bookmarks,
  children,
}: {
  bookmarks: Bookmark[];
  children: ReactNode;
}) {
  const [bookmarkState, setBookmarkListState] = useState(bookmarks);

  const bookmarkActions = useMemo(
    () => ({
      updateContent: ({
        bookmarkId,
        newContent,
      }: {
        bookmarkId: number;
        newContent: string;
      }) => {
        setBookmarkListState((prev) => {
          const newBookmarkList = prev.map((bookmark) => {
            if (bookmark.bookmarkId === bookmarkId) {
              return {
                ...bookmark,
                content: newContent,
              };
            }

            return bookmark;
          });

          return newBookmarkList;
        });
      },
      updatePage: ({
        bookmarkId,
        newPage,
      }: {
        bookmarkId: number;
        newPage: number;
      }) => {
        setBookmarkListState((prev) => {
          const newBookmarkList = prev.map((bookmark) => {
            if (bookmark.bookmarkId === bookmarkId) {
              return {
                ...bookmark,
                page: newPage,
              };
            }

            return bookmark;
          });

          return newBookmarkList;
        });
      },
      deleteBookmark: ({ bookmarkId }: { bookmarkId: number }) => {
        setBookmarkListState((prev) => {
          const newBookmarkList = prev.filter(
            (bookmark) => bookmark.bookmarkId !== bookmarkId
          );

          return newBookmarkList;
        });
      },
    }),
    []
  );

  return (
    <BookmarkListStateContext.Provider value={bookmarkState}>
      <BookmarkListActionsContext.Provider value={bookmarkActions}>
        {children}
      </BookmarkListActionsContext.Provider>
    </BookmarkListStateContext.Provider>
  );
}
