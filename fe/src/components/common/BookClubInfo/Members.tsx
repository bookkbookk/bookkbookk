import { BookClubInfo } from "@api/bookClub/type";
import { Stack, Typography } from "@mui/material";

export default function Members({
  members,
}: {
  members: BookClubInfo["members"];
}) {
  const memberNicknames = members
    .slice(0, 5)
    .map((m) => m.nickname)
    .join(", ");
  const memberCount = members.length;
  const memberText =
    memberCount > 5
      ? `${memberNicknames} 외 ${memberCount - 5}명`
      : memberNicknames;

  return (
    <Stack gap={1} display="flex" flexDirection="row">
      <Typography variant="body2" sx={{ fontWeight: 700 }}>
        멤버
      </Typography>
      <Typography variant="body2">{memberText}</Typography>
    </Stack>
  );
}
