import { ChapterListItem } from "@api/chapters/type";
import StatusChip from "@components/common/StatusChip";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { TreeItem } from "@mui/x-tree-view";
import { TopicItem } from "./TopicItem";

export function ChapterItem({ chapter }: { chapter: ChapterListItem }) {
  const { chapterId, title } = chapter;

  return (
    <Stack
      display={"flex"}
      flexDirection={"row"}
      width={"90%"}
      gap={2}
      padding={1}>
      <StatusChip statusId={chapter.statusId} />
      <TreeItem
        sx={{ width: "90%" }}
        nodeId={chapterId + ""}
        label={<Typography variant="h5">{title}</Typography>}>
        <Stack gap={1} paddingY={1}>
          {chapter.topics.map((topic) => (
            <TopicItem key={topic.topicId} topic={topic} />
          ))}
        </Stack>
      </TreeItem>
    </Stack>
  );
}
