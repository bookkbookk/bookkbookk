import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { queryKeys } from "./../queryKeys";
import {
  deleteComment,
  deleteReaction,
  getComments,
  getReactions,
  patchComment,
  postComment,
  postReaction,
} from "./client";
import { NewCommentBody, Reaction } from "./type";

export const useGetComments = ({ bookmarkId }: { bookmarkId: number }) => {
  const { data: bookmarks } = useSuspenseQuery({
    ...queryKeys.comments.list({ bookmarkId }),
    queryFn: () => getComments(bookmarkId),
  });

  return bookmarks;
};

export const usePostComment = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: postComment,
  });

  const onPostComment = (newCommentBody: NewCommentBody) => {
    mutate(newCommentBody, {
      onSuccess: () => {
        queryClient.invalidateQueries(
          queryKeys.comments.list({
            bookmarkId: newCommentBody.bookmarkId,
          })
        );
        onSuccessCallback();
      },
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_COMMENT_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostComment };
};

export const useMutateComment = ({
  bookmarkId,
  commentId,
  onSuccessCallback,
}: {
  bookmarkId: number;
  commentId: number;
  onSuccessCallback: () => void;
}) => {
  const queryClient = useQueryClient();

  const invalidateCommentListQuery = () => {
    queryClient.invalidateQueries(queryKeys.comments.list({ bookmarkId }));
    onSuccessCallback();
  };

  const { mutate: mutatePatchComment } = useMutation({
    mutationFn: patchComment,
  });

  const { mutate: mutateDeleteComment } = useMutation({
    mutationFn: deleteComment,
  });

  const onPatchComment = ({ content }: { content: string }) => {
    mutatePatchComment(
      { commentId, content },
      {
        onSuccess: invalidateCommentListQuery,
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_COMMENT_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  const onDeleteComment = () => {
    mutateDeleteComment(commentId, {
      onSuccess: invalidateCommentListQuery,
      onError: () => {
        enqueueSnackbar(MESSAGE.DELETE_COMMENT_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPatchComment, onDeleteComment };
};

export const useGetReactions = ({ commentId }: { commentId: number }) => {
  const { data: reactions } = useSuspenseQuery({
    ...queryKeys.comments.reactions({ commentId }),
    queryFn: () => getReactions(commentId),
  });

  return reactions;
};

export const useCommentReaction = ({ commentId }: { commentId: number }) => {
  const queryClient = useQueryClient();

  const { mutate: mutatePostReaction } = useMutation({
    mutationFn: postReaction,
  });

  const { mutate: mutateDeleteReaction } = useMutation({
    mutationFn: deleteReaction,
  });

  const onDeleteReaction = (reactionName: keyof Reaction) => {
    mutateDeleteReaction(
      { commentId, reactionName },
      {
        onSuccess: () => {
          queryClient.invalidateQueries(
            queryKeys.comments.reactions({ commentId })
          );
        },
        onError: () => {
          enqueueSnackbar(MESSAGE.DELETE_REACTION_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  const onPostReaction = (reactionName: keyof Reaction) => {
    mutatePostReaction(
      { commentId, reactionName },
      {
        onSuccess: () => {
          queryClient.invalidateQueries(
            queryKeys.comments.reactions({ commentId })
          );
        },
        onError: () => {
          enqueueSnackbar(MESSAGE.POST_REACTION_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPostReaction, onDeleteReaction };
};
