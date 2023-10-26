import { Box } from "@mui/material";
import { useTopicListValue } from "store/newBook/useTopicList";
import { TopicListItem } from "./TopicListItem";

export function TopicList({
  chapterTitle,
  chapterIndex,
}: {
  chapterTitle: string;
  chapterIndex: number;
}) {
  const topicList = useTopicListValue();
  const topics = topicList.get(chapterIndex);

  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        padding: "1rem 0",
        gap: "0.5rem",
      }}>
      {topics?.map((topic, index) => (
        <TopicListItem
          key={`chapter${chapterIndex}-${chapterTitle}-topic-${index}-${topic.title}`}
          chapterIndex={chapterIndex}
          topicIndex={index}
          topic={topic}
        />
      ))}
    </Box>
  );
}
