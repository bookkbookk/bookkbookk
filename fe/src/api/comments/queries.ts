import { MESSAGE } from "@constant/index";
import {
  useMutation,
  useQueryClient,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { enqueueSnackbar } from "notistack";
import { queryKeys } from "./../queryKeys";
import { getComments, postComment } from "./client";
import { NewCommentBody } from "./type";

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
