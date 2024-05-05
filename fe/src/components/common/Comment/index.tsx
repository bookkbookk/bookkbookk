import CommentContainer from "./CommentContainer";
import CommentContent from "./CommentContent";
import CommentFooter from "./CommentFooter";
import CommentHeader from "./CommentHeader";

export const Comment = Object.assign(CommentContainer, {
  Header: CommentHeader,
  Content: CommentContent,
  Footer: CommentFooter,
});
