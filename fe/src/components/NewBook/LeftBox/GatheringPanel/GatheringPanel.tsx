import { Box, Typography } from "@mui/material";
import { useActiveTabValue } from "store/useNewBook";
import NewBookTabPanel from "../NewBookTabPanel";
import { BookClubChoice } from "./BookClubChoice";

export default function GatheringPanel({ index }: { index: number }) {
  const activeTabID = useActiveTabValue();

  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          gap: "4rem",
          padding: "1rem",
        }}>
        <BookClubChoice />
        <Box sx={{ display: "flex", flexDirection: "column", gap: "1rem" }}>
          <Typography variant="h3">모임 생성하기</Typography>
          {/* <GatheringTable /> */}
        </Box>
      </Box>
    </NewBookTabPanel>
  );
}
