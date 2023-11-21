import { ChapterListItem, TopicItemInfo } from "@api/chapters/type";
import { Topic } from "@api/topics/type";
import ChapterTitle from "@components/BookChapter/LeftBox/ChapterTitle/ChapterTitle";
import TopicTitle from "@components/BookChapter/LeftBox/TopicTitle/TopicTitle";
import TopicListCard from "@components/BookChapter/RightBox/TopicListCard";
import { BoxContent, LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";
import { useState } from "react";
import { Location, useLocation } from "react-router-dom";

type BookChapterState = {
  chapter: ChapterListItem;
  topic: Pick<TopicItemInfo, "topicId" | "title">;
};

export default function BookChapter() {
  const {
    state: {
      chapter: { chapterId, statusId, title: chapterTitle },
      topic,
    },
  }: Location<BookChapterState> = useLocation();

  const [currentTopic, setCurrentTopic] = useState<Topic>(topic);
  const onTopicChange = (newTopic: Topic) => setCurrentTopic(newTopic);
  const handleTopicTitleChange = (newTitle: string) =>
    setCurrentTopic((prev) => ({ ...prev, title: newTitle }));

  return (
    <Box sx={{ display: "flex", padding: 4 }}>
      <LeftBox>
        <ChapterTitle
          chapterId={chapterId}
          title={chapterTitle}
          statusId={statusId}
        />
        <TopicTitle
          topicTitleInfo={currentTopic}
          onTopicTitleChange={handleTopicTitleChange}
        />
      </LeftBox>
      <RightBox>
        <BoxContent>
          <TopicListCard
            chapterId={chapterId}
            currentTopicId={currentTopic.topicId}
            onTopicChange={onTopicChange}
          />
        </BoxContent>
      </RightBox>
    </Box>
  );
}
