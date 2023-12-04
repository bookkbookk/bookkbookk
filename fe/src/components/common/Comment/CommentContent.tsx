import { BlockNoteView, useBlockNote } from "@blocknote/react";
import { Stack } from "@mui/material";
import { editorTheme } from "@styles/theme/palette";
import { useThemeModeValue } from "store/useTheme";
import { CommentBody } from "./style";

export default function CommentContent({
  content,
  isEditing,
  onContentChange,
}: {
  content: string;
  isEditing: boolean;
  onContentChange: (content: string) => void;
}) {
  return (
    <CommentBody>
      <Stack width="100%">
        {isEditing ? (
          <Editor content={content} onChange={onContentChange} />
        ) : (
          <Viewer content={content} />
        )}
      </Stack>
    </CommentBody>
  );
}

function Viewer({ content }: { content: string }) {
  const themeMode = useThemeModeValue();
  const blocks = JSON.parse(content);

  const viewer = useBlockNote({
    initialContent: blocks,
    editable: false,
  });

  return <BlockNoteView editor={viewer} theme={editorTheme[themeMode]} />;
}

function Editor({
  content,
  onChange,
}: {
  content: string;
  onChange: (content: string) => void;
}) {
  const themeMode = useThemeModeValue();
  const blocks = JSON.parse(content);

  const editor = useBlockNote({
    initialContent: blocks,
    editable: true,
    onEditorContentChange: async (editor) => {
      onChange(JSON.stringify(editor.topLevelBlocks));
    },
  });

  return <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />;
}
