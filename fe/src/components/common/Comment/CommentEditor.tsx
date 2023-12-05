import { BlockNoteView, useBlockNote } from "@blocknote/react";
import { editorTheme } from "@styles/theme/palette";
import { useThemeModeValue } from "store/useTheme";
import { CommentBody } from "./style";

export default function CommentEditor({
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

  return (
    <CommentBody>
      <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
    </CommentBody>
  );
}
