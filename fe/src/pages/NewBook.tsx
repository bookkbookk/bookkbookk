import NewBookLeftBox from "@components/NewBook/LeftBox";
import NewBookInfo from "@components/NewBook/RightBox";
import { LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";

export default function NewBook() {
  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <LeftBox>
        <NewBookLeftBox />
      </LeftBox>
      <RightBox>
        <NewBookInfo />
      </RightBox>
    </Box>
  );
}
