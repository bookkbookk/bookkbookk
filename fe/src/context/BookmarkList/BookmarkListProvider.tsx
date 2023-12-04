import { useGetBookmarks } from "@api/bookmarks/queries";
import { ReactNode, useMemo, useState } from "react";
import { BookmarkListState } from "../type";
import {
  BookmarkListActionsContext,
  BookmarkListStateContext,
} from "./BookmarkListContext";

export function BookmarkListProvider({
  topicId,
  children,
}: {
  topicId: number;
  children: ReactNode;
}) {
  const bookmarks = useGetBookmarks({ topicId });
  const [bookmarkState, setBookmarkListState] =
    useState<BookmarkListState>(bookmarks);

  const bookmarkActions = useMemo(
    () => ({
      setContent: ({
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
      setPage: ({
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
