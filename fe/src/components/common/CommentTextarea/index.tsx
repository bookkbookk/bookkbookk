import BookmarkPageField from "./BookmarkPageField";
import CommentTextareaFooter from "./CommentTextareaFooter";
import CommentTextareaWrapper from "./CommentTextareaWrapper";
import ContentField from "./ContentField";

export const CommentTextarea = Object.assign(CommentTextareaWrapper, {
  PageField: BookmarkPageField,
  Content: ContentField,
  Footer: CommentTextareaFooter,
});
