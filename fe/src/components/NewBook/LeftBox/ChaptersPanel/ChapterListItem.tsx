import ClearIcon from "@mui/icons-material/Clear";
import { Box, Button, IconButton, Typography } from "@mui/material";
import { useRef } from "react";
import { Info } from "store/newBook/type";
import { useSetChapterList } from "store/newBook/useChapterList";
import { useSetTopicList } from "store/newBook/useTopicList";
import { TopicList } from "./TopicList";
import { ChapterTextField } from "./style";

export function ChapterListItem({
  index,
  chapter,
}: {
  index: number;
  chapter: Info;
}) {
  const chapterTitleRef = useRef<HTMLInputElement>(null);
  const setChapterList = useSetChapterList();
  const setTopicList = useSetTopicList();

  const saveChapterInfo = () => {
    setChapterList({
      type: "UPDATE_CHAPTER",
      payload: {
        chapterIndex: index,
        info: {
          title: chapterTitleRef.current?.value || "",
        },
      },
    });
  };

  const onClickDeleteChapter = () => {
    setChapterList({
      type: "DELETE_CHAPTER",
      payload: {
        chapterIndex: index,
      },
    });
    setTopicList({
      type: "DELETE_CHAPTER",
      payload: { chapterIndex: index },
    });
  };

  const onClickAddTopic = () => {
    setTopicList({
      type: "ADD_TOPIC",
      payload: { chapterIndex: index },
    });
  };

  return (
    <Box sx={{ display: "flex", padding: "2rem", position: "relative" }}>
      <Typography variant="h1">{`${index + 1}`.padStart(2, "0")}</Typography>
      <Box sx={{ width: "100%", height: "100%", padding: "0 1rem" }}>
        <Box sx={{ display: "flex" }}>
          <ChapterTextField
            defaultValue={chapter.title}
            inputRef={chapterTitleRef}
            onBlur={saveChapterInfo}
            variant="standard"
            placeholder={`챕터 ${index + 1} 제목을 입력하세요`}
          />
        </Box>
        <TopicList chapterTitle={chapter.title} chapterIndex={index} />
        <Button
          variant="outlined"
          onClick={onClickAddTopic}
          color="inherit"
          sx={{ width: "100%" }}>
          새로운 토픽 추가
        </Button>
      </Box>
      <IconButton
        aria-label="delete-chapter"
        size="medium"
        onClick={onClickDeleteChapter}
        sx={{ position: "absolute", top: 5, right: 5 }}>
        <ClearIcon fontSize="inherit" />
      </IconButton>
    </Box>
  );
}
