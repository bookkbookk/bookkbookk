import { ChapterListItem, TopicItemInfo } from "@api/chapters/type";
import { Topic } from "@api/topics/type";
import BookmarkList from "@components/BookChapter/LeftBox/BookmarkList/BookmarkList";
import ChapterTitle from "@components/BookChapter/LeftBox/ChapterTitle/ChapterTitle";
import NewBookmark from "@components/BookChapter/LeftBox/NewBookmark";
import TopicTitle from "@components/BookChapter/LeftBox/TopicTitle/TopicTitle";
import TopicListCard from "@components/BookChapter/RightBox/TopicListCard";
import ChapterBookCard from "@components/BookDetail/ChapterBookCard";
import {
  AddFab,
  BoxContent,
  LeftBox,
  RightBox,
} from "@components/common/common.style";
import BookmarkAddIcon from "@mui/icons-material/BookmarkAdd";
import { Box, Divider, Tooltip } from "@mui/material";
import { BookmarkListProvider } from "context/BookmarkList/BookmarkListProvider";
import { NewBookmarkProvider } from "context/NewBookmark/NewBookmarkProvider";
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

  const [isNewBookmarkOpen, setIsNewBookmarkOpen] = useState(false);
  const toggleNewBookmark = () => setIsNewBookmarkOpen((prev) => !prev);

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
        <BookmarkListProvider topicId={currentTopic.topicId}>
          <BookmarkList />
        </BookmarkListProvider>
        {isNewBookmarkOpen && (
          <NewBookmarkProvider>
            <NewBookmark
              topicId={currentTopic.topicId}
              toggleNewBookmark={toggleNewBookmark}
            />
          </NewBookmarkProvider>
        )}
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
      <Tooltip title="새로운 북마크를 추가해보세요">
        <AddFab color="primary" aria-label="add" onClick={toggleNewBookmark}>
          <BookmarkAddIcon />
        </AddFab>
      </Tooltip>
    </Box>
  );
}
