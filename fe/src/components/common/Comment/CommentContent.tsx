import { PartialBlock } from "@blocknote/core";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import { Stack } from "@mui/material";
import { editorTheme } from "@styles/theme/palette";
import { useState } from "react";
import { useThemeModeValue } from "store/useTheme";
import { CommentBody } from "./style";

export default function CommentContent({
  content,
  isEditing,
}: {
  content: string;
  isEditing: boolean;
}) {
  const themeMode = useThemeModeValue();
  const [blocks, setBlocks] = useState<PartialBlock[]>(
    JSON.parse(content) || []
  );

  const editor = useBlockNote({
    initialContent: blocks,
    editable: false,
    onEditorContentChange: async (editor) => {
      setBlocks(editor.topLevelBlocks);
    },
  });

  const editableEditor = useBlockNote({
    initialContent: blocks,
    editable: true,
    onEditorContentChange: async (editor) => {
      setBlocks(editor.topLevelBlocks);
    },
  });

  return (
    <CommentBody>
      <Stack width="100%">
        {isEditing ? (
          <BlockNoteView
            editor={editableEditor}
            theme={editorTheme[themeMode]}
          />
        ) : (
          <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
        )}
      </Stack>
    </CommentBody>
  );
}
