import { usePostComment } from "@api/comments/queries";
import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import { Stack } from "@mui/material";
import Button from "@mui/material/Button";
import { editorTheme } from "@styles/theme/palette";
import {
  useNewCommentActions,
  useNewCommentState,
} from "context/NewComment/useNewComment";
import { enqueueSnackbar } from "notistack";
import { useThemeModeValue } from "store/useTheme";

export default function CommentContentField({
  bookmarkId,
  toggleReplying,
}: {
  bookmarkId: number;
  toggleReplying: () => void;
}) {
  const { content } = useNewCommentState();
  const { setContent } = useNewCommentActions();
  const themeMode = useThemeModeValue();

  const editor = useBlockNote({
    onEditorContentChange: async (editor) => {
      setContent(JSON.stringify(editor.topLevelBlocks));
    },
  });

  const { onPostComment } = usePostComment({
    onSuccessCallback: toggleReplying,
  });

  const postComment = () => {
    if (!content) {
      return enqueueSnackbar("댓글 내용은 필수로 입력해야 해요!", {
        variant: "error",
      });
    }

    onPostComment({ bookmarkId, content });
  };

  return (
    <Stack display="flex" padding={2}>
      {/* TODO: Drag Handle Menu element 가려지는 문제 해결 필요 */}
      <Stack minHeight={80} flexGrow={1}>
        <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
      </Stack>
      <Stack display="flex" flexDirection="row" alignSelf="end" gap={1}>
        <Button
          variant="outlined"
          startIcon={<ClearIcon />}
          size="small"
          onClick={toggleReplying}
          sx={{ alignSelf: "flex-end" }}>
          작성 취소
        </Button>
        <Button
          variant="contained"
          startIcon={<CheckIcon />}
          size="small"
          onClick={postComment}
          sx={{ alignSelf: "flex-end" }}>
          작성 완료
        </Button>
      </Stack>
    </Stack>
  );
}
