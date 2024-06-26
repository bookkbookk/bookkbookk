import { BookListItem } from "@api/book/type";
import { ChapterListItem, TopicItemInfo } from "@api/chapters/type";
import ChapterStatusMenu from "@components/BookChapter/LeftBox/ChapterStatusMenu";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { TreeItem } from "@mui/x-tree-view";
import { Location, useLocation, useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { TopicItem } from "./TopicItem";

export function ChapterItem({ chapter }: { chapter: ChapterListItem }) {
  const { chapterId, title } = chapter;
  const chapterPrefix = "chapter_";
  const navigate = useNavigate();
  const {
    state: { book },
  }: Location<{ book: BookListItem }> = useLocation();

  const onTopicClick = (topic: Pick<TopicItemInfo, "topicId" | "title">) => {
    navigate(`${ROUTE_PATH.chapter}/${chapterId}`, {
      state: {
        chapter,
        topic,
        book,
      },
    });
  };

  return (
    <Stack
      display={"flex"}
      flexDirection={"row"}
      width={"90%"}
      gap={2}
      padding={1}>
      <Stack>
        <ChapterStatusMenu
          statusId={chapter.statusId}
          chapterId={chapter.chapterId}
        />
      </Stack>
      <TreeItem
        sx={{ width: "90%" }}
        nodeId={chapterPrefix + chapterId}
        label={<Typography variant="h5">{title}</Typography>}>
        <Stack gap={1} paddingY={1}>
          {chapter.topics.map((topic) => (
            <TopicItem
              key={topic.topicId}
              topic={topic}
              onClick={onTopicClick}
            />
          ))}
        </Stack>
      </TreeItem>
    </Stack>
  );
}
