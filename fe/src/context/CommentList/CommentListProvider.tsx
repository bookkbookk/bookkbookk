import { Comment } from "@api/comments/type";
import { ReactNode, useMemo, useState } from "react";
import {
  CommentListActionsContext,
  CommentListStateContext,
} from "./CommentListContext";

export function CommentListProvider({
  commentList,
  children,
}: {
  commentList: Comment[];
  children: ReactNode;
}) {
  const [comments, setComments] = useState(commentList);

  const commentsAction = useMemo(
    () => ({
      updateContent: ({
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
      deleteComment: ({ commentId }: { commentId: number }) => {
        setComments((prev) => {
          const newCommentList = prev.filter((c) => c.commentId !== commentId);

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
