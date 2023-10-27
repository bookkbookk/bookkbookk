import BookClubTabs from "@components/BookClub/BookClubTabs";
import { BoxHeader, MainBox } from "@components/common/common.style";
import { BOOK_CLUB_TAB } from "@components/constants";
import AddIcon from "@mui/icons-material/Add";
import { Button } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function BookClub() {
  const [activeTabID, setActiveTabID] = useState(BOOK_CLUB_TAB[0].id);
  const isLogin = useIsLoginValue();
  const navigate = useNavigate();

  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };

  return (
    <MainBox>
      <BoxHeader>
        <BookClubTabs {...{ activeTabID, handleChange }} />
        {isLogin && (
          <Button
            variant="contained"
            endIcon={<AddIcon />}
            onClick={() => navigate(ROUTE_PATH.newBookClub)}>
            새로운 북클럽 추가하기
          </Button>
        )}
      </BoxHeader>
    </MainBox>
  );
}
