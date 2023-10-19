import { LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";

export default function BookClub() {
  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <LeftBox>left</LeftBox>
      <RightBox>right</RightBox>
    </Box>
  );
}
