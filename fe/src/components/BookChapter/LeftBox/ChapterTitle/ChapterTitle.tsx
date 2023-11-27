import { ChapterListItem } from "@api/chapters/type";
import DeleteIcon from "@mui/icons-material/Delete";
import ModeRoundedIcon from "@mui/icons-material/ModeRounded";
import { Button, Stack, Typography } from "@mui/material";
import { useState } from "react";
import ChapterStatusMenu from "../ChapterStatusMenu";
import { ChapterTitleEditor } from "./ChapterTitleEditor";

export default function ChapterTitle(
  chapterTitleInfo: Pick<ChapterListItem, "chapterId" | "title" | "statusId">
) {
  const { chapterId, statusId, title } = chapterTitleInfo;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const [chapterTitle, setChapterTitle] = useState(title);
  const onTitleChange = (newTitle: string) => {
    setChapterTitle(newTitle);
    toggleEditing();
  };

  return (
    <Stack
      display="flex"
      flexDirection="row"
      width="100%"
      alignItems="center"
      gap={2}>
      <ChapterStatusMenu {...{ chapterId, statusId }} />
      {isEditing ? (
        <ChapterTitleEditor
          defaultValue={chapterTitle}
          {...{ toggleEditing, isEditing, onTitleChange, chapterId }}
        />
      ) : (
        <Typography variant="h6" width="100%">
          {chapterTitle}
        </Typography>
      )}
      {!isEditing && (
        <Stack
          display="flex"
          flexDirection="row"
          gap={1}
          minWidth="30%"
          justifyContent="end">
          <Button
            variant="outlined"
            size="small"
            startIcon={<ModeRoundedIcon fontSize="small" />}
            onClick={toggleEditing}>
            챕터 제목 편집
          </Button>
          <Button
            variant="outlined"
            size="small"
            startIcon={<DeleteIcon fontSize="small" />}>
            챕터 삭제
          </Button>
        </Stack>
      )}
    </Stack>
  );
}
