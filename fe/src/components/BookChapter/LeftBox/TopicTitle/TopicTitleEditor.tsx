import { usePatchTopic } from "@api/topics/queries";
import CheckIcon from "@mui/icons-material/Check";
import CloseIcon from "@mui/icons-material/Close";
import { Button, Stack, TextField } from "@mui/material";
import { useState } from "react";
import { useParams } from "react-router-dom";

export function TopicTitleEditor({
  defaultValue,
  toggleEditing,
  onTitleChange,
  topicId,
}: {
  defaultValue: string;
  toggleEditing: () => void;
  onTitleChange: (newTitle: string) => void;
  topicId: number;
}) {
  const { chapterId } = useParams<{ chapterId: string }>();
  const [topicTitle, setTopicTitle] = useState(defaultValue);
  const { onPatchTopicTitle } = usePatchTopic({ onTitleChange });

  const onEditTopicTitle = () => {
    onPatchTopicTitle({
      chapterId: Number(chapterId),
      topicId,
      title: topicTitle,
    });
  };

  return (
    <Stack display="flex" flexDirection="row" gap={2} width="100%">
      <TextField
        id="chapter-title"
        variant="standard"
        value={topicTitle}
        sx={{ flexGrow: 1 }}
        onChange={(e) => setTopicTitle(e.target.value)}
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
          onClick={onEditTopicTitle}>
          편집 완료
        </Button>
      </Stack>
    </Stack>
  );
}
