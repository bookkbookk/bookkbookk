import ReplyIcon from "@mui/icons-material/Reply";
import { Button, Stack } from "@mui/material";
import ReactionMenu from "./ReactionMenu";

export default function ActionFooter({
  onReplyButtonClick,
}: {
  onReplyButtonClick?: () => void;
}) {
  return (
    <Stack
      display="flex"
      flexDirection="row"
      justifyContent="space-between"
      padding={1}>
      <ReactionMenu />
      {onReplyButtonClick && (
        <Button
          color="inherit"
          size="small"
          startIcon={<ReplyIcon />}
          onClick={onReplyButtonClick}>
          Reply
        </Button>
      )}
    </Stack>
  );
}
