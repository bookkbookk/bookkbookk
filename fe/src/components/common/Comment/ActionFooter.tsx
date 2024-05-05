import { Reaction } from "@api/comments/type";
import ReplyIcon from "@mui/icons-material/Reply";
import { Button, Stack } from "@mui/material";
import ReactionList from "./ReactionList/ReactionList";
import ReactionMenu from "./ReactionMenu";

export default function ActionFooter({
  reactions,
  onReplyButtonClick,
  onReactionClick,
}: {
  reactions: Partial<Reaction>;
  onReplyButtonClick?: () => void;
  onReactionClick: (reactionName: keyof Reaction) => void;
}) {
  const isReactionsEmpty = Object.values(reactions).every(
    (reaction) => !reaction.length
  );

  return (
    <Stack
      display="flex"
      flexDirection="row"
      justifyContent="space-between"
      padding={1}>
      <Stack display="flex" flexDirection="row" gap={1}>
        <ReactionMenu reactions={reactions} onReactionClick={onReactionClick} />
        {!isReactionsEmpty && (
          <ReactionList
            reactions={reactions}
            onReactionClick={onReactionClick}
          />
        )}
      </Stack>
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
