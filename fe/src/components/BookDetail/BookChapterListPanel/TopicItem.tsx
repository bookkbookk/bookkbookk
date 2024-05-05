import { TopicItemInfo } from "@api/chapters/type";
import LabelImportantIcon from "@mui/icons-material/LabelImportant";
import { styled } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import { TreeItem } from "@mui/x-tree-view";

export function TopicItem({
  topic,
  onClick,
}: {
  topic: TopicItemInfo;
  onClick: (topic: Pick<TopicItemInfo, "topicId" | "title">) => void;
}) {
  const { topicId, title, recentBookmark } = topic;

  return (
    <TreeItem
      nodeId={topicId + ""}
      icon={<LabelImportantIcon />}
      onClick={() => onClick({ topicId, title })}
      label={
        <Stack display={"flex"} flexDirection={"row"} gap={1}>
          <Content sx={{ width: "60%" }}>{title}</Content>
          <Stack display={"flex"} flexDirection={"row"} gap={1} width={"60%"}>
            <Avatar
              src={recentBookmark.authorProfileImgUrl}
              sx={{ width: 24, height: 24 }}
            />
            <Content>{recentBookmark.content}</Content>
          </Stack>
        </Stack>
      }
    />
  );
}

export const Content = styled("p")`
  font: ${({ theme }) => theme.typography.body1};
  color: ${({ theme }) => theme.palette.text.secondary};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;
