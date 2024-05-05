import { Reaction } from "@api/comments/type";
import { REACTIONS } from "@components/constants";
import { Chip, Stack, Tooltip, Typography } from "@mui/material";
import { useMemberValue } from "store/useMember";

export function ReactionChip({
  reactionName,
  memberNameList,
  onClick,
}: {
  reactionName: keyof Reaction;
  memberNameList: string[];
  onClick: (reactionName: keyof Reaction) => void;
}) {
  const member = useMemberValue();
  const members = memberNameList.join(", ");

  const isReacted = memberNameList.includes(member?.nickname ?? "");

  return (
    <Tooltip title={members} arrow placement="top">
      <Chip
        color={isReacted ? "secondary" : "default"}
        onClick={() => onClick(reactionName)}
        label={
          <Stack gap={1} display="flex" flexDirection="row" alignItems="center">
            <Typography>
              {String.fromCodePoint(
                parseInt(REACTIONS[reactionName].unicode, 16)
              )}
            </Typography>
            <Typography fontWeight={600} fontSize={14}>
              {memberNameList.length}
            </Typography>
          </Stack>
        }
      />
    </Tooltip>
  );
}
