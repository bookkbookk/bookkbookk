import { Chip } from "@mui/material";

export default function BookClubStatusChip({
  bookClubStatus,
}: {
  bookClubStatus: "open" | "closed";
}) {
  const isOpenBookClub = bookClubStatus === "open";

  return (
    <Chip
      label={isOpenBookClub ? "OPEN" : "CLOSED"}
      size="small"
      color={isOpenBookClub ? "primary" : "secondary"}
      sx={{ fontWeight: 700, width: "fit-content" }}
    />
  );
}
