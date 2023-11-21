import { TopicItemInfo } from "@api/chapters/type";
import DeleteIcon from "@mui/icons-material/Delete";
import LabelImportantIcon from "@mui/icons-material/LabelImportant";
import ModeRoundedIcon from "@mui/icons-material/ModeRounded";
import { Button, Stack, Typography } from "@mui/material";
import { useState } from "react";
import { TopicTitleEditor } from "./TopicTitleEditor";

export default function TopicTitle(
  topicTitleInfo: Pick<TopicItemInfo, "topicId" | "title">
) {
  const { topicId, title } = topicTitleInfo;

  const [isEditing, setIsEditing] = useState(false);
  const toggleEditing = () => setIsEditing((prev) => !prev);

  const [topicTitle, setTopicTitle] = useState(title);
  const onTitleChange = (newTitle: string) => {
    setTopicTitle(newTitle);
    toggleEditing();
  };

  return (
    <Stack
      display="flex"
      flexDirection="row"
      width="100%"
      justifyContent="space-between"
      alignItems="center"
      paddingLeft={10}>
      <Stack
        display="flex"
        flexDirection="row"
        alignItems="center"
        gap={0.5}
        width="100%">
        <LabelImportantIcon />
        {isEditing ? (
          <TopicTitleEditor
            defaultValue={topicTitle}
            {...{ toggleEditing, isEditing, onTitleChange, topicId }}
          />
        ) : (
          <Typography variant="h6" width="100%">
            {topicTitle}
          </Typography>
        )}
      </Stack>
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
            onClick={toggleEditing}
            startIcon={<ModeRoundedIcon fontSize="small" />}>
            토픽 제목 편집
          </Button>
          <Button
            variant="outlined"
            size="small"
            startIcon={<DeleteIcon fontSize="small" />}>
            토픽 삭제
          </Button>
        </Stack>
      )}
    </Stack>
  );
}
