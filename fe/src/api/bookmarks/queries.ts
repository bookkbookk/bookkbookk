import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { queryKeys } from "./../queryKeys";
import { getBookmarks, postBookmark } from "./client";
import { NewBookmarkBody } from "./type";

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
