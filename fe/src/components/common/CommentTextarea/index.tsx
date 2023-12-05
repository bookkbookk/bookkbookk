import BookmarkContentField from "./BookmarkContentField";
import { BookmarkPageEditor, BookmarkPageViewer } from "./BookmarkPageField";
import CommentContentField from "./CommentContentField";
import CommentTextareaWrapper from "./CommentTextareaWrapper";

export const CommentTextarea = Object.assign(CommentTextareaWrapper, {
  PageEditor: BookmarkPageEditor,
  PageViewer: BookmarkPageViewer,
  Bookmark: BookmarkContentField,
  Comment: CommentContentField,
});
