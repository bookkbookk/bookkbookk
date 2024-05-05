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

export const usePatchComment = ({
  commentId,
  onSuccessCallback,
}: {
  commentId: number;
  onSuccessCallback: ({ updatedContent }: { updatedContent: string }) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: patchComment,
  });

  const onPatchComment = ({ content }: { content: string }) => {
    mutate(
      { commentId, content },
      {
        onSuccess: () => onSuccessCallback({ updatedContent: content }),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_COMMENT_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPatchComment };
};

export const useDeleteComment = ({
  commentId,
  onSuccessCallback,
}: {
  commentId: number;
  onSuccessCallback: () => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: deleteComment,
  });

  const onDeleteComment = () => {
    mutate(commentId, {
      onSuccess: onSuccessCallback,
      onError: () => {
        enqueueSnackbar(MESSAGE.DELETE_COMMENT_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onDeleteComment };
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
