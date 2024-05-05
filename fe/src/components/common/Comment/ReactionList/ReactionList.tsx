import { Reaction } from "@api/comments/type";
import { Stack } from "@mui/material";
import { ReactionChip } from "./ReactionChip";

export default function ReactionList({
  reactions,
  onReactionClick,
}: {
  reactions: Partial<Reaction>;
  onReactionClick: (reactionName: keyof Reaction) => void;
}) {
  const reactionList = Object.entries(reactions) as [
    keyof Reaction,
    string[]
  ][];

  return (
    <Stack display="flex" flexDirection="row" gap={0.5}>
      {reactionList.map(([reactionName, memberNames]) => {
        return (
          <ReactionChip
            key={reactionName}
            reactionName={reactionName}
            memberNameList={memberNames}
            onClick={onReactionClick}
          />
        );
      })}
    </Stack>
  );
}
