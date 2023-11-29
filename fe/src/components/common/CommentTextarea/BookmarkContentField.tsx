import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import BookmarkAddIcon from "@mui/icons-material/BookmarkAdd";
import { Stack } from "@mui/material";
import Button from "@mui/material/Button";
import { editorTheme } from "@styles/theme/palette";
import { useNewBookmarkActions } from "context/NewBookmark/useNewBookmark";
import { useThemeModeValue } from "store/useTheme";

export default function BookmarkContentField() {
  const { setContent } = useNewBookmarkActions();
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
      <Stack minHeight={120} flexGrow={1}>
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
