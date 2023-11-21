import { ChapterListItem, TopicItemInfo } from "@api/chapters/type";
import ChapterTitle from "@components/BookChapter/ChapterTitle";
import TopicListCard from "@components/BookChapter/TopicListCard";
import TopicTitle from "@components/BookChapter/TopicTitle";
import { BoxContent, LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";
import { Location, useLocation } from "react-router-dom";

type BookChapterState = {
  chapter: ChapterListItem;
  topic: Pick<TopicItemInfo, "topicId" | "title">;
};

export default function BookChapter() {
  const {
    state: {
      chapter: { chapterId, statusId, title: chapterTitle },
      topic: { topicId, title: topicTitle },
    },
  }: Location<BookChapterState> = useLocation();

  return (
    <Box sx={{ display: "flex", padding: 4 }}>
      <LeftBox>
        <ChapterTitle
          chapterId={chapterId}
          title={chapterTitle}
          statusId={statusId}
        />
        <TopicTitle topicId={topicId} title={topicTitle} />
      </LeftBox>
      <RightBox>
        <BoxContent>
          <TopicListCard chapterId={chapterId} currentTopicId={topicId} />
        </BoxContent>
      </RightBox>
    </Box>
  );
}
