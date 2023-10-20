import NewBookFunnel from "@components/NewBook/LeftBox/Funnel";
import NewBookInfo from "@components/NewBook/RightBox/NewBookInfo";
import { LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";

export default function NewBook() {
  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <LeftBox>
        <NewBookFunnel />
      </LeftBox>
      <RightBox>
        <NewBookInfo />
      </RightBox>
    </Box>
  );
}
