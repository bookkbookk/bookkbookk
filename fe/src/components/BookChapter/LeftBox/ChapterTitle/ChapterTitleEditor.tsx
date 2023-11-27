import { usePatchChapter } from "@api/chapters/queries";
import CheckIcon from "@mui/icons-material/Check";
import CloseIcon from "@mui/icons-material/Close";
import { Button, Stack, TextField } from "@mui/material";
import { useState } from "react";

export function ChapterTitleEditor({
  defaultValue,
  toggleEditing,
  onTitleChange,
  chapterId,
}: {
  defaultValue: string;
  toggleEditing: () => void;
  onTitleChange: (newTitle: string) => void;
  chapterId: number;
}) {
  const [chapterTitle, setChapterTitle] = useState(defaultValue);
  const { onPatchChapterTitle } = usePatchChapter({ onTitleChange });

  const onEditChapterTitle = () => {
    onPatchChapterTitle({ chapterId, title: chapterTitle });
  };

  return (
    <Stack display="flex" flexDirection="row" gap={2} width="100%">
      <TextField
        id="chapter-title"
        variant="standard"
        value={chapterTitle}
        sx={{ flexGrow: 1 }}
        onChange={(e) => setChapterTitle(e.target.value)}
      />
      <Stack display="flex" flexDirection="row" gap={1} alignSelf="flex-end">
        <Button
          aria-label="cancel-editing-chapter-title"
          size="small"
          startIcon={<CloseIcon fontSize="small" />}
          variant="outlined"
          onClick={toggleEditing}>
          편집 취소
        </Button>
        <Button
          aria-label="edit-chapter-title"
          size="small"
          variant="contained"
          startIcon={<CheckIcon fontSize="small" />}
          onClick={onEditChapterTitle}>
          편집 완료
        </Button>
      </Stack>
    </Stack>
  );
}
