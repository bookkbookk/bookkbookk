import ClearIcon from "@mui/icons-material/Clear";
import { Box, Button, IconButton, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { Chapter, Info, useSetChapterList } from "store/newBook/useChapters";
import { ChapterTextField } from "./style";

export function ChapterListItem({
  index,
  chapter,
}: {
  index: number;
  chapter: Chapter;
}) {
  const [chapterTitle, setChapterTitle] = useState(chapter.title);
  const setChapters = useSetChapterList();

  const onChangeChapterTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setChapterTitle(e.target.value);
    setChapters({
      type: "UPDATE_CHAPTER",
      payload: {
        chapterIndex: index,
        info: {
          title: e.target.value,
        },
      },
    });
  };

  const onClickDeleteChapter = () => {
    setChapters({
      type: "DELETE_CHAPTER",
      payload: {
        chapterIndex: index,
      },
    });
  };

  const onClickAddTopic = () => {
    setChapters({
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
            value={chapterTitle}
            onChange={onChangeChapterTitle}
            variant="standard"
            placeholder={`챕터 ${index + 1} 제목을 입력하세요`}
          />
        </Box>
        {chapter.topics && (
          <TopicList
            chapterTitle={chapter.title}
            chapterIndex={index}
            topics={chapter.topics}
          />
        )}
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

function TopicList({
  chapterTitle,
  chapterIndex,
  topics,
}: {
  chapterTitle: string;
  chapterIndex: number;
  topics: Info[];
}) {
  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        padding: "1rem 0",
        gap: "0.5rem",
      }}>
      {topics.map((topic, index) => (
        <TopicListItem
          key={`chapter${chapterIndex}-${chapterTitle}-topic-${index}`}
          chapterIndex={chapterIndex}
          topicIndex={index}
          topic={topic}
        />
      ))}
    </Box>
  );
}

function TopicListItem({
  chapterIndex,
  topicIndex,
  topic,
}: {
  chapterIndex: number;
  topicIndex: number;
  topic: Info;
}) {
  const setChapters = useSetChapterList();
  const [topicTitle, setTopicTitle] = useState(topic.title);
  const onChangeTopicTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTopicTitle(e.target.value);
    setChapters({
      type: "UPDATE_TOPIC",
      payload: {
        chapterIndex,
        topicIndex,
        info: {
          title: e.target.value,
        },
      },
    });
  };
  const onClickDeleteTopic = () => {
    setChapters({
      type: "DELETE_TOPIC",
      payload: {
        chapterIndex,
        topicIndex,
      },
    });
  };

  return (
    <Box sx={{ display: "flex", gap: "0.5rem" }}>
      <TextField
        value={topicTitle}
        onChange={onChangeTopicTitle}
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
    </Box>
  );
}
