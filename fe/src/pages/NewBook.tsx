import NewBookLeftBox from "@components/NewBook/LeftBox";
import NewBookInfo from "@components/NewBook/RightBox/NewBookInfo";
import { RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";

export default function NewBook() {
  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <NewBookLeftBox />
      <RightBox>
        <NewBookInfo />
      </RightBox>
    </Box>
  );
}
