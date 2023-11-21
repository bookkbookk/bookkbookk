import { useGetTopics } from "@api/topics/queries";
import { Card, CardContent } from "@components/common/common.style";
import { List, ListItem, ListItemButton, Typography } from "@mui/material";

export default function TopicListCard({
  chapterId,
  currentTopicId,
}: {
  chapterId: number;
  currentTopicId: number;
}) {
  const topicList = useGetTopics({ chapterId });

  return (
    <Card>
      <Typography variant="h6">토픽 목록</Typography>
      <CardContent>
        <List sx={{ width: "100%", padding: 0 }}>
          {topicList.map(({ topicId, title }) => (
            <ListItem key={topicId} sx={{ padding: 0 }}>
              <ListItemButton
                selected={currentTopicId === topicId}
                onClick={() => console.log("TODO: topic id 상태 변경")}>
                {title}
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </CardContent>
    </Card>
  );
}
