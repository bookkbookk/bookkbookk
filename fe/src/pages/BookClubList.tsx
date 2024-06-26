import Tabs from "@components/common/Tabs";
import { BoxHeader, MainBox } from "@components/common/common.style";
import { BOOK_CLUB_STATUS, BOOK_CLUB_TAB } from "@components/constants";
import AddIcon from "@mui/icons-material/Add";
import Button from "@mui/material/Button";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function BookClubList() {
  const [activeTabID, setActiveTabID] = useState<number>(
    BOOK_CLUB_STATUS.ALL.id
  );
  const isLogin = useIsLoginValue();
  const navigate = useNavigate();

  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };

  return (
    <MainBox>
      <BoxHeader>
        <Tabs {...{ activeTabID, handleChange, tabList: BOOK_CLUB_TAB }} />
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
