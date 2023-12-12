import { Stack, Typography } from "@mui/material";
import { formatDate } from "@utils/index";

export default function UpcomingGatheringDate({
  upcomingGatheringDate,
}: {
  upcomingGatheringDate: string;
}) {
  const upcomingGathering = formatDate(upcomingGatheringDate);

  return (
    <Stack gap={1} display="flex" flexDirection="row">
      <Typography variant="body2" sx={{ fontWeight: 700 }}>
        다가오는 모임일
      </Typography>
      <Typography variant="body2">{upcomingGathering}</Typography>
    </Stack>
  );
}
