import { ChapterListItem, TopicItemInfo } from "@api/chapters/type";
import { Topic } from "@api/topics/type";
import BookmarkList from "@components/BookChapter/LeftBox/BookmarkList/BookmarkList";
import ChapterTitle from "@components/BookChapter/LeftBox/ChapterTitle/ChapterTitle";
import NewBookmark from "@components/BookChapter/LeftBox/NewBookmark";
import TopicTitle from "@components/BookChapter/LeftBox/TopicTitle/TopicTitle";
import TopicListCard from "@components/BookChapter/RightBox/TopicListCard";
import ChapterBookCard from "@components/BookDetail/ChapterBookCard";
import { BoxContent, LeftBox, RightBox } from "@components/common/common.style";
import { Box, Divider } from "@mui/material";
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
        <Divider sx={{ width: "100%" }} />
        <BookmarkList topicId={currentTopic.topicId} />
        <NewBookmark topicId={currentTopic.topicId} />
      </LeftBox>
      <RightBox>
        <BoxContent>
          <ChapterBookCard />
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
