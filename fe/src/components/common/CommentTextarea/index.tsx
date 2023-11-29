import BookmarkContentField from "./BookmarkContentField";
import BookmarkPageField from "./BookmarkPageField";
import CommentContentField from "./CommentContentField";
import CommentTextareaWrapper from "./CommentTextareaWrapper";

export const CommentTextarea = Object.assign(CommentTextareaWrapper, {
  PageField: BookmarkPageField,
  Bookmark: BookmarkContentField,
  Comment: CommentContentField,
});
