import ClearIcon from "@mui/icons-material/Clear";
import { IconButton, Stack, TextField } from "@mui/material";
import { useRef } from "react";
import { Info } from "store/type";
import { useSetTopicList } from "store/useTopicList";

export function TopicListItem({
  chapterIndex,
  topicIndex,
  topic,
}: {
  chapterIndex: number;
  topicIndex: number;
  topic: Info;
}) {
  const topicTitleRef = useRef<HTMLInputElement>(null);
  const setTopicList = useSetTopicList();

  const saveTopicInfo = () => {
    setTopicList({
      type: "UPDATE_TOPIC",
      payload: {
        chapterIndex,
        topicIndex,
        info: {
          title: topicTitleRef.current?.value || "",
        },
      },
    });
  };

  const onClickDeleteTopic = () => {
    setTopicList({
      type: "DELETE_TOPIC",
      payload: {
        chapterIndex,
        topicIndex,
      },
    });
  };

  return (
    <Stack display="flex" flexDirection="row" gap={1}>
      <TextField
        inputRef={topicTitleRef}
        defaultValue={topic.title}
        onBlur={saveTopicInfo}
        variant="standard"
        placeholder="토픽 제목을 입력하세요"
        sx={{ width: "100%" }}
      />
      <IconButton
        aria-label="delete-chapter"
        size="small"
        onClick={onClickDeleteTopic}>
        <ClearIcon fontSize="inherit" />
      </IconButton>
    </Stack>
  );
}
