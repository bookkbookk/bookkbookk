import NewBookClubFunnel from "@components/NewBookClub/LeftBox/Funnel";
import NewBookClubInfo from "@components/NewBookClub/RightBox/Info";
import { LeftBox, RightBox } from "@components/common/common.style";
import { Box } from "@mui/material";

export default function NewBookClub() {
  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <LeftBox>
        <NewBookClubFunnel />
      </LeftBox>
      <RightBox>
        <NewBookClubInfo />
      </RightBox>
    </Box>
  );
}
