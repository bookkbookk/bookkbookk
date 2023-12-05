import { useGetComments } from "@api/comments/queries";
import { ReactNode, useMemo, useState } from "react";
import { CommentListState } from "../type";
import {
  CommentListActionsContext,
  CommentListStateContext,
} from "./CommentListContext";

export function CommentListProvider({
  bookmarkId,
  children,
}: {
  bookmarkId: number;
  children: ReactNode;
}) {
  const commentList = useGetComments({ bookmarkId });
  const [comments, setComments] = useState<CommentListState>(commentList);

  const commentsAction = useMemo(
    () => ({
      setContent: ({
        commentId,
        newContent,
      }: {
        commentId: number;
        newContent: string;
      }) => {
        setComments((prev) => {
          const newCommentList = prev.map((c) => {
            if (c.commentId === commentId) {
              return {
                ...c,
                content: newContent,
              };
            }

            return c;
          });

          return newCommentList;
        });
      },
    }),
    []
  );

  return (
    <CommentListStateContext.Provider value={comments}>
      <CommentListActionsContext.Provider value={commentsAction}>
        {children}
      </CommentListActionsContext.Provider>
    </CommentListStateContext.Provider>
  );
}
