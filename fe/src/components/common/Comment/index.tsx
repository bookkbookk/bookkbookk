import ActionFooter from "./ActionFooter";
import ButtonFooter from "./ButtonFooter";
import CommentContainer from "./CommentContainer";
import CommentEditor from "./CommentEditor";
import CommentHeader from "./CommentHeader";
import CommentContent from "./CommentContent";
import CommentFooter from "./CommentFooter";
import CommentViewer from "./CommentViewer";
import PageEditor from "./PageEditor";
import PageViewer from "./PageViewer";

export const Comment = Object.assign(CommentContainer, {
  Header: CommentHeader,
  Content: CommentContent,
  Footer: CommentFooter,
  ContentViewer: CommentViewer,
  ContentEditor: CommentEditor,
  PageEditor: PageEditor,
  PageViewer: PageViewer,
  ActionFooter: ActionFooter,
  ButtonFooter: ButtonFooter,
});
