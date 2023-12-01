import { Bookmark as BookmarkProps } from "@api/bookmarks/type";
import { Comment } from "@components/common/Comment";
import { BookmarkProvider } from "context/BookmarkProvider/BookmarkProvider";
import { useState } from "react";
import BookmarkPageField from "./BookmarkPageField";

export default function Bookmark({
  bookmark,
  toggleReplying,
}: {
  bookmark: BookmarkProps;
  toggleReplying: () => void;
}) {
  const { author, createdTime, content, page } = bookmark;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const isVisiblePageField = !!page || isEditing;
  const pageStr = page?.toString() ?? "";

  return (
    <BookmarkProvider bookmark={{ content, page: pageStr }}>
      <Comment>
        <Comment.Header
          {...{ author, createdTime, isEditing, toggleEditing }}
        />
        {isVisiblePageField && <BookmarkPageField {...{ isEditing }} />}
        <Comment.Content {...{ content, isEditing }} />
        <Comment.Footer onReplyButtonClick={toggleReplying} />
      </Comment>
    </BookmarkProvider>
  );
}
