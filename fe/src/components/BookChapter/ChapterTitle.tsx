import { ChapterListItem } from "@api/chapters/type";
import DeleteIcon from "@mui/icons-material/Delete";
import ModeRoundedIcon from "@mui/icons-material/ModeRounded";
import { Button, Stack, Typography } from "@mui/material";
import { useState } from "react";
import ChapterStatusMenu from "./ChapterStatusMenu";

export default function ChapterTitle(
  chapterTitleInfo: Pick<ChapterListItem, "chapterId" | "title" | "statusId">
) {
  const { chapterId, statusId, title } = chapterTitleInfo;

  const [chapterTitle, setChapterTitle] = useState(title);
  const [chapterStatus, setChapterStatus] = useState(statusId);

  return (
    <Stack
      display="flex"
      flexDirection="row"
      width="100%"
      justifyContent="space-between"
      alignItems="center"
      gap={2}>
      <Stack display="flex" flexDirection="row" alignItems="center" gap={1}>
        <ChapterStatusMenu {...{ chapterId, statusId }} />
        <Typography variant="h4">{chapterTitle}</Typography>
      </Stack>
      <Stack display="flex" flexDirection="row" gap={1} alignSelf="flex-end">
        <Button variant="outlined" size="small" sx={{ gap: 0.5 }}>
          <ModeRoundedIcon fontSize="small" />
          챕터 제목 편집
        </Button>
        <Button variant="outlined" size="small" sx={{ gap: 0.5 }}>
          <DeleteIcon fontSize="small" />
          챕터 삭제
        </Button>
      </Stack>
    </Stack>
  );
}
