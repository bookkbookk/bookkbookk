import {
  useBookmarkReaction,
  useDeleteBookmark,
  useGetReactions,
  usePatchBookmark,
} from "@api/bookmarks/queries";
import { Bookmark as BookmarkType } from "@api/bookmarks/type";
import { Reaction } from "@api/comments/type";
import { Comment } from "@components/common/Comment";
import { useBookmarkListActions } from "context/BookmarkList/useBookmarkList";
import { useState } from "react";
import { useMemberValue } from "store/useMember";

export default function Bookmark({
  bookmark: { author, createdTime, content, page, bookmarkId },
  toggleReplying,
}: {
  bookmark: BookmarkType;
  toggleReplying: () => void;
}) {
  const reactions = useGetReactions({ bookmarkId });
  const member = useMemberValue();

  const { onPatchBookmark } = usePatchBookmark({
    bookmarkId,
    onSuccessCallback: ({ updatedContent, updatedPage }) => {
      updatedContent &&
        updateContent({ bookmarkId, newContent: updatedContent });
      (updatedPage || updatedPage === 0) &&
        updatePage({ bookmarkId, newPage: updatedPage });

      toggleEditing();
    },
  });
  const { onDeleteBookmark } = useDeleteBookmark({
    bookmarkId,
    onSuccessCallback: () => {
      deleteBookmark({ bookmarkId });
    },
  });
  const { onPostReaction, onDeleteReaction } = useBookmarkReaction({
    bookmarkId,
  });

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

  const { updatePage, updateContent, deleteBookmark } =
    useBookmarkListActions();

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

  const onReactionClick = (reactionName: keyof Reaction) => {
    const isChecked = reactions[reactionName]?.includes(member?.nickname || "");

    if (isChecked) {
      onDeleteReaction(reactionName);
    } else {
      onPostReaction(reactionName);
    }
  };

  return (
    <Comment>
      <Comment.Header
        {...{ author, createdTime, isEditing, toggleEditing }}
        onCancelClick={cancelEditing}
        onCompleteClick={patchBookmark}
        onDeleteClick={onDeleteBookmark}
      />
      {!!page && !isEditing && <Comment.PageViewer value={updatedPage + ""} />}
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
      <Comment.ActionFooter
        reactions={reactions}
        onReplyButtonClick={toggleReplying}
        onReactionClick={onReactionClick}
      />
    </Comment>
  );
}
