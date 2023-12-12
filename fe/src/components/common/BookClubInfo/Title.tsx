import { Stack, Typography } from "@mui/material";
import { formatDate } from "@utils/index";

export default function Title({
  name,
  createdTime,
  closedTime,
}: {
  name: string;
  createdTime: string;
  closedTime?: string;
}) {
  const formattedCreatedTime = formatDate(createdTime);
  const formattedClosedTime = closedTime && formatDate(closedTime);
  const subheaderText = `${formattedCreatedTime} ~ ${
    formattedClosedTime || "진행중"
  }`;

  return (
    <Stack display="flex" alignItems="center">
      <Typography variant="h4">{name}</Typography>
      <Typography variant="body2" color="text.secondary">
        {subheaderText}
      </Typography>
    </Stack>
  );
}
