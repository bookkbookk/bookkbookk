import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import { editorTheme } from "@styles/theme/palette";
import { useThemeModeValue } from "store/useTheme";
import { CommentBody } from "./style";

export default function CommentViewer({ content }: { content: string }) {
  const themeMode = useThemeModeValue();
  const blocks = JSON.parse(content);

  const viewer = useBlockNote({
    initialContent: blocks,
    editable: false,
  });

  return (
    <CommentBody>
      <BlockNoteView editor={viewer} theme={editorTheme[themeMode]} />
    </CommentBody>
  );
}
