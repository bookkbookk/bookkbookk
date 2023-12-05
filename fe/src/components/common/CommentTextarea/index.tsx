import { BookmarkPageEditor, BookmarkPageViewer } from "./BookmarkPageField";
import CommentTextareaContent from "./CommentTextareaContent";
import CommentTextareaFooter from "./CommentTextareaFooter";
import CommentTextareaWrapper from "./CommentTextareaWrapper";

export const CommentTextarea = Object.assign(CommentTextareaWrapper, {
  PageEditor: BookmarkPageEditor,
  PageViewer: BookmarkPageViewer,
  Content: CommentTextareaContent,
  Footer: CommentTextareaFooter,
});
