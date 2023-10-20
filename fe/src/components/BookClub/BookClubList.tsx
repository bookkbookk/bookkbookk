import AddIcon from "@mui/icons-material/Add";
import { Box, Button } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";
import BookClubTabs from "./BookClubTabs";
import { BOOK_CLUB_TAB } from "./constants";

export default function BookClubList() {
  const [activeTabID, setActiveTabID] = useState(BOOK_CLUB_TAB[0].id);
  const isLogin = useIsLoginValue();
  const navigate = useNavigate();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };

  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        justifyContent: "space-between",
        padding: "1rem",
      }}>
      <BookClubTabs {...{ activeTabID, handleChange }} />
      {isLogin && (
        <Button
          variant="contained"
          endIcon={<AddIcon />}
          onClick={() => navigate(ROUTE_PATH.newBookClub)}>
          새로운 북클럽 추가하기
        </Button>
      )}
      {/* TODO: 북클럽 조회 */}
      {/* <CustomTabPanel value={value} index={0}>
        Item One
      </CustomTabPanel>
      <CustomTabPanel value={value} index={1}>
        Item Two
      </CustomTabPanel>
      <CustomTabPanel value={value} index={2}>
        Item Three
      </CustomTabPanel> */}
    </Box>
  );
}
