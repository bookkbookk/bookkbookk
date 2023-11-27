import { CommentTextarea } from "@components/common/CommentTextarea";

export default function NewBookmark({ topicId }: { topicId: number }) {
  // TODO: 새로운 북마크 추가 Context 만들기
  return (
    <CommentTextarea>
      <CommentTextarea.PageField />
      <CommentTextarea.Content />
      <CommentTextarea.Footer />
    </CommentTextarea>
  );
}
