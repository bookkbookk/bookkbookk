import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import { Stack } from "@mui/material";
import { editorTheme } from "@styles/theme/palette";
import { useThemeModeValue } from "store/useTheme";

export default function CommentTextareaContent({
  onChange,
}: {
  onChange: (content: string) => void;
}) {
  const themeMode = useThemeModeValue();

  const editor = useBlockNote({
    onEditorContentChange: async (editor) => {
      onChange(JSON.stringify(editor.topLevelBlocks));
    },
  });

  return (
    <Stack minHeight={80} flexGrow={1}>
      <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
    </Stack>
  );
}
