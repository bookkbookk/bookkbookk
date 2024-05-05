import { Reaction } from "@api/comments/type";
import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { queryKeys } from "./../queryKeys";
import {
  deleteBookmark,
  deleteReaction,
  getBookmarks,
  getReactions,
  patchBookmark,
  postBookmark,
  postReaction,
} from "./client";
import { NewBookmarkBody, PatchBookmarkBody } from "./type";

export const useGetBookmarks = ({ topicId }: { topicId: number }) => {
  const { data: bookmarks } = useSuspenseQuery({
    ...queryKeys.bookmarks.list({ topicId }),
    queryFn: () => getBookmarks(topicId),
  });

  return bookmarks;
};

export const usePostNewBookmark = ({
  onSuccessCallback,
}: {
  onSuccessCallback: () => void;
}) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: postBookmark,
  });

  const onPostNewBookmark = (newBookmarkBody: NewBookmarkBody) => {
    mutate(newBookmarkBody, {
      onSuccess: () => {
        queryClient.invalidateQueries(
          queryKeys.bookmarks.list({
            topicId: newBookmarkBody.topicId,
          })
        );
        onSuccessCallback();
      },
      onError: () => {
        enqueueSnackbar(MESSAGE.NEW_BOOKMARK_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onPostNewBookmark };
};

export const usePatchBookmark = ({
  bookmarkId,
  onSuccessCallback,
}: {
  bookmarkId: number;
  onSuccessCallback: ({
    updatedContent,
    updatedPage,
  }: {
    updatedContent?: string;
    updatedPage?: number;
  }) => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: patchBookmark,
  });

  const onPatchBookmark = ({ page, content }: Partial<PatchBookmarkBody>) => {
    mutate(
      { bookmarkId, page, content },
      {
        onSuccess: () =>
          onSuccessCallback({ updatedContent: content, updatedPage: page }),
        onError: () => {
          enqueueSnackbar(MESSAGE.UPDATE_BOOKMARK_ERROR, {
            variant: "error",
          });
        },
      }
    );
  };

  return { onPatchBookmark };
};

export const useDeleteBookmark = ({
  bookmarkId,
  onSuccessCallback,
}: {
  bookmarkId: number;
  onSuccessCallback: () => void;
}) => {
  const { mutate } = useMutation({
    mutationFn: deleteBookmark,
  });

  const onDeleteBookmark = () => {
    mutate(bookmarkId, {
      onSuccess: onSuccessCallback,
      onError: () => {
        enqueueSnackbar(MESSAGE.DELETE_BOOKMARK_ERROR, {
          variant: "error",
        });
      },
    });
  };

  return { onDeleteBookmark };
};

export const useGetReactions = ({ bookmarkId }: { bookmarkId: number }) => {
  const { data: reactions } = useSuspenseQuery({
    ...queryKeys.bookmarks.reactions({ bookmarkId }),
    queryFn: () => getReactions(bookmarkId),
  });

  return reactions;
};

export const useBookmarkReaction = ({ bookmarkId }: { bookmarkId: number }) => {
  const queryClient = useQueryClient();

  const { mutate: mutatePostReaction } = useMutation({
    mutationFn: postReaction,
  });

  const { mutate: mutateDeleteReaction } = useMutation({
    mutationFn: deleteReaction,
  });

  const onPostReaction = (reactionName: keyof Reaction) => {
    mutatePostReaction(
      { bookmarkId, reactionName },
      {
        onSuccess: () => {
          queryClient.invalidateQueries(
            queryKeys.bookmarks.reactions({ bookmarkId })
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

  const onDeleteReaction = (reactionName: keyof Reaction) => {
    mutateDeleteReaction(
      { bookmarkId, reactionName },
      {
        onSuccess: () => {
          queryClient.invalidateQueries(
            queryKeys.bookmarks.reactions({ bookmarkId })
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

  return { onPostReaction, onDeleteReaction };
};
