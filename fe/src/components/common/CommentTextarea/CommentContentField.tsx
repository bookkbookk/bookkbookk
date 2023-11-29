import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import BookmarkAddIcon from "@mui/icons-material/BookmarkAdd";
import { Stack } from "@mui/material";
import Button from "@mui/material/Button";
import { editorTheme } from "@styles/theme/palette";
import { useBookmarkCommentActions } from "context/BookmarkComment/useBookmarkComment";
import { useThemeModeValue } from "store/useTheme";

// TODO: BookmarkContentField.tsx와 중복되는 코드를 어떻게 줄일 수 있을지 고민해보기
export default function CommentContentField() {
  const { setContent } = useBookmarkCommentActions();
  const themeMode = useThemeModeValue();

  const editor = useBlockNote({
    onEditorContentChange: async (editor) => {
      const content = await editor.blocksToMarkdown(editor.topLevelBlocks);
      setContent(content);
    },
  });

  return (
    <Stack display="flex" padding={2}>
      {/* TODO: Drag Handle Menu element 가려지는 문제 해결 필요 */}
      <Stack minHeight={80} flexGrow={1}>
        <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
      </Stack>
      <Button
        variant="contained"
        startIcon={<BookmarkAddIcon />}
        size="small"
        sx={{ alignSelf: "flex-end" }}>
        작성 완료
      </Button>
    </Stack>
  );
}
