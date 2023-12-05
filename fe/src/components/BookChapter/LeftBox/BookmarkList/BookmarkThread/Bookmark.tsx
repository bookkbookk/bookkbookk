import { usePatchBookmark } from "@api/bookmarks/queries";
import { Bookmark as BookmarkType } from "@api/bookmarks/type";
import { Comment } from "@components/common/Comment";
import { useBookmarkListActions } from "context/BookmarkList/useBookmarkList";
import { useState } from "react";

export default function Bookmark({
  bookmark: { author, createdTime, content, page, bookmarkId },
  toggleReplying,
}: {
  bookmark: BookmarkType;
  toggleReplying: () => void;
}) {
  const [updatedContent, setUpdatedContent] = useState(content);
  const onBookmarkContentChange = (content: string) => {
    setUpdatedContent(content);
  };

  const [updatedPage, setUpdatedPage] = useState(page + "");
  const onBookmarkPageChange = (updatedPage: string) => {
    setUpdatedPage(updatedPage);
  };

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const { setPage, setContent } = useBookmarkListActions();
  const { onPatchBookmark } = usePatchBookmark({
    bookmarkId,
    onSuccessCallback: ({ updatedContent, updatedPage }) => {
      updatedContent && setContent({ bookmarkId, newContent: updatedContent });
      (updatedPage || updatedPage === 0) &&
        setPage({ bookmarkId, newPage: updatedPage });

      toggleEditing();
    },
  });

  const patchBookmark = () => {
    const pageNum = Number(updatedPage);

    if (content === updatedContent && page === pageNum) {
      toggleEditing();
      return;
    }

    onPatchBookmark({
      content: updatedContent,
      page: pageNum,
    });
  };

  const cancelEditing = () => {
    setUpdatedContent(content);
    setUpdatedPage(page + "");
    toggleEditing();
  };

  // TODO: 북마크 삭제 요청

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, isEditing, toggleEditing }}
        onCancelClick={cancelEditing}
        onCompleteClick={patchBookmark}
      />
      {!!page && !isEditing && (
        <Comment.PageViewer value={updatedPage + ""} disabled={true} />
      )}
      {isEditing && (
        <Comment.PageEditor
          value={page ? page + "" : ""}
          onChange={onBookmarkPageChange}
        />
      )}
      {isEditing ? (
        <Comment.ContentEditor
          content={updatedContent}
          onChange={onBookmarkContentChange}
        />
      ) : (
        <Comment.ContentViewer content={content} />
      )}
      <Comment.ActionFooter onReplyButtonClick={toggleReplying} />
    </Comment>
  );
}
