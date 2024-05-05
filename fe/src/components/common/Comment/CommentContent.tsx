import ReactMarkdown from "react-markdown";
import { CommentBody } from "./style";

export default function CommentContent({ content }: { content: string }) {
  return (
    <CommentBody>
      <ReactMarkdown children={content} />
    </CommentBody>
  );
}
