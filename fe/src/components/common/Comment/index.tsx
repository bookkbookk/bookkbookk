import CommentContainer from "./CommentContainer";
import CommentEditor from "./CommentEditor";
import CommentFooter from "./CommentFooter";
import CommentHeader from "./CommentHeader";
import CommentViewer from "./CommentViewer";

export const Comment = Object.assign(CommentContainer, {
  Header: CommentHeader,
  Viewer: CommentViewer,
  Editor: CommentEditor,
  Footer: CommentFooter,
});
