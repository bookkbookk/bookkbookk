import { TopicItemInfo } from "@api/chapters/type";
import DeleteIcon from "@mui/icons-material/Delete";
import LabelImportantIcon from "@mui/icons-material/LabelImportant";
import ModeRoundedIcon from "@mui/icons-material/ModeRounded";
import { Button, Stack, Typography } from "@mui/material";
import { useState } from "react";

export default function TopicTitle(
  topicTitleInfo: Pick<TopicItemInfo, "topicId" | "title">
) {
  const { topicId, title } = topicTitleInfo;

  const [topicTitle, setTopicTitle] = useState(title);

  return (
    <Stack
      display="flex"
      flexDirection="row"
      width="100%"
      justifyContent="space-between"
      alignItems="center"
      paddingLeft={10}>
      <Stack display="flex" flexDirection="row" alignItems="center" gap={0.5}>
        <LabelImportantIcon />
        <Typography variant="h6">{topicTitle}</Typography>
      </Stack>
      <Stack display="flex" flexDirection="row" gap={1} alignSelf="flex-end">
        <Button variant="outlined" size="small" sx={{ gap: 0.5 }}>
          <ModeRoundedIcon fontSize="small" />
          토픽 제목 편집
        </Button>
        <Button variant="outlined" size="small" sx={{ gap: 0.5 }}>
          <DeleteIcon fontSize="small" />
          토픽 삭제
        </Button>
      </Stack>
    </Stack>
  );
}
