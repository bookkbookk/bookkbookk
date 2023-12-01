import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { Comment } from "@components/common/Comment";
import { useState } from "react";

export default function Bookmark({
  bookmark,
  toggleReplying,
}: {
  bookmark: BookmarkProps;
  toggleReplying: () => void;
}) {
  const { bookmarkId, author, createdTime, content, page } = bookmark;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  return (
    <Comment>
      <Comment.Header {...{ author, createdTime, isEditing, toggleEditing }} />
      <Comment.Content {...{ content, isEditing }} />
      <Comment.Footer onReplyButtonClick={toggleReplying} />
    </Comment>
  );
}
