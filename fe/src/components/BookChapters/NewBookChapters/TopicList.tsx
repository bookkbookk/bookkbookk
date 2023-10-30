import { Stack } from "@mui/material";
import { useTopicListValue } from "store/useTopicList";
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
    <Stack width="100%" flex="column" spacing={1} paddingY={2}>
      {topics?.map((topic, index) => (
        <TopicListItem
          key={`chapter${chapterIndex}-${chapterTitle}-topic-${index}-${topic.title}`}
          chapterIndex={chapterIndex}
          topicIndex={index}
          topic={topic}
        />
      ))}
    </Stack>
  );
}
