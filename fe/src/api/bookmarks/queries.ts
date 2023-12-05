import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { queryKeys } from "./../queryKeys";
import { getBookmarks, patchBookmark, postBookmark } from "./client";
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
