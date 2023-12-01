import { usePostNewBookmark } from "@api/bookmarks/queries";
import "@blocknote/core/style.css";
import { BlockNoteView, useBlockNote } from "@blocknote/react";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import { Stack } from "@mui/material";
import Button from "@mui/material/Button";
import { editorTheme } from "@styles/theme/palette";
import {
  useBookmarkState,
  useNewBookmarkActions,
} from "context/BookmarkProvider/useBookmark";
import { enqueueSnackbar } from "notistack";
import { useThemeModeValue } from "store/useTheme";

export default function BookmarkContentField({
  topicId,
  toggleNewBookmark,
}: {
  topicId: number;
  toggleNewBookmark: () => void;
}) {
  const { page, content } = useBookmarkState();
  const { setPage, setContent } = useNewBookmarkActions();

  const themeMode = useThemeModeValue();

  const editor = useBlockNote({
    onEditorContentChange: async (editor) => {
      setContent(JSON.stringify(editor.topLevelBlocks));
    },
  });

  const { onPostNewBookmark } = usePostNewBookmark({
    onSuccessCallback: () => {
      toggleNewBookmark();
      setPage("");
      setContent("");
    },
  });

  const postNewBookmark = () => {
    const pageNumber = page ? parseInt(page) : undefined;

    if (!content) {
      return enqueueSnackbar("북마크 내용은 필수로 입력해야 해요!", {
        variant: "error",
      });
    }

    onPostNewBookmark({ topicId, content, page: pageNumber });
  };

  return (
    <Stack display="flex" padding={2}>
      {/* TODO: Drag Handle Menu element 가려지는 문제 해결 필요 */}
      <Stack minHeight={120} flexGrow={1}>
        <BlockNoteView editor={editor} theme={editorTheme[themeMode]} />
      </Stack>
      <Stack display="flex" flexDirection="row" alignSelf="end" gap={1}>
        <Button
          variant="outlined"
          startIcon={<ClearIcon />}
          size="small"
          sx={{ alignSelf: "flex-end" }}>
          작성 취소
        </Button>
        <Button
          variant="contained"
          startIcon={<CheckIcon />}
          size="small"
          onClick={postNewBookmark}
          sx={{ alignSelf: "flex-end" }}>
          작성 완료
        </Button>
      </Stack>
    </Stack>
  );
}
