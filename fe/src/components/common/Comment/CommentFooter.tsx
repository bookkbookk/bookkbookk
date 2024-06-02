import ReplyIcon from "@mui/icons-material/Reply";
import { Button, Stack } from "@mui/material";

export default function CommentFooter({
  onReplyButtonClick,
}: {
  onReplyButtonClick: () => void;
}) {
  return (
    <Stack
      display="flex"
      flexDirection="row"
      justifyContent="space-between"
      padding={1}>
      <Button
        color="inherit"
        startIcon={<ReplyIcon />}
        onClick={onReplyButtonClick}>
        Reply
      </Button>
    </Stack>
  );
}
