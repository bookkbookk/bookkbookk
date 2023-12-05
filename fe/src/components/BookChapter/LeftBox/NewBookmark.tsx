import { usePostNewBookmark } from "@api/bookmarks/queries";
import { CommentTextarea } from "@components/common/CommentTextarea";
import { Target } from "@components/common/common.style";
import useAutoScroll from "@hooks/useAutoScroll";
import { enqueueSnackbar } from "notistack";
import { useState } from "react";

export default function NewBookmark({
  topicId,
  toggleNewBookmark,
}: {
  topicId: number;
  toggleNewBookmark: () => void;
}) {
  const targetRef = useAutoScroll();
  const [bookmarkPage, setBookmarkPage] = useState("");
  const [bookmarkContent, setBookmarkContent] = useState("");

  const onContentChange = (content: string) => {
    setBookmarkContent(content);
  };

  const onPageChange = (page: string) => {
    setBookmarkPage(page);
  };

  const { onPostNewBookmark } = usePostNewBookmark({
    onSuccessCallback: toggleNewBookmark,
  });

  const postNewBookmark = () => {
    const pageNumber = Number(bookmarkPage);

    if (!bookmarkContent) {
      return enqueueSnackbar("북마크 내용은 필수로 입력해야 해요!", {
        variant: "error",
      });
    }

    onPostNewBookmark({
      topicId,
      content: bookmarkContent,
      page: pageNumber,
    });
  };

  return (
    <>
      <Target ref={targetRef} />
      <CommentTextarea>
        <CommentTextarea.PageEditor
          value={bookmarkPage}
          onChange={onPageChange}
        />
        <CommentTextarea.Content onChange={onContentChange} />
        <CommentTextarea.Footer
          onCancelClick={toggleNewBookmark}
          onPostClick={postNewBookmark}
        />
      </CommentTextarea>
    </>
  );
}
