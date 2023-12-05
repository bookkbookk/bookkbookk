import { usePatchBookmark } from "@api/bookmarks/queries";
import { Bookmark as BookmarkType } from "@api/bookmarks/type";
import { Comment } from "@components/common/Comment";
import { useBookmarkListActions } from "context/BookmarkList/useBookmarkList";
import { useState } from "react";
import BookmarkPageField from "../BookmarkPageField";

export default function Bookmark({
  bookmark: { author, createdTime, content, page, bookmarkId },
  toggleReplying,
}: {
  bookmark: BookmarkType;
  toggleReplying: () => void;
}) {
  const [updatedContent, setUpdatedContent] = useState(content);
  const [updatedPage, setUpdatedPage] = useState(page);

  const onBookmarkContentChange = (content: string) => {
    setUpdatedContent(content);
  };

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const isVisiblePageField = !!page || isEditing;

  const { setContent } = useBookmarkListActions();
  const { onPatchBookmark } = usePatchBookmark({
    bookmarkId,
    onSuccessCallback: ({ updatedContent }) => {
      updatedContent && setContent({ bookmarkId, newContent: updatedContent });
      toggleEditing();
    },
  });

  const patchBookmark = () => {
    if (content === updatedContent && page === updatedPage) {
      toggleEditing();
      return;
    }

    onPatchBookmark({
      content: updatedContent,
      page: updatedPage,
    });
  };

  const cancelEditing = () => {
    setUpdatedContent(content);
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
      {isVisiblePageField && <BookmarkPageField {...{ isEditing }} />}
      {isEditing ? (
        <Comment.Editor
          content={updatedContent}
          onChange={onBookmarkContentChange}
        />
      ) : (
        <Comment.Viewer content={content} />
      )}
      <Comment.Footer onReplyButtonClick={toggleReplying} />
    </Comment>
  );
}
