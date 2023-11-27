import BookList from "@components/Library/BookList";
import LibraryTabs from "@components/Library/LibraryTabs";
import { AddFab, BoxHeader, MainBox } from "@components/common/common.style";
import { TOTAL_TAB } from "@components/constants";
import CollectionsBookmarkIcon from "@mui/icons-material/CollectionsBookmark";
import LibraryAddIcon from "@mui/icons-material/LibraryAdd";
import { Tooltip } from "@mui/material";
import Button from "@mui/material/Button";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function Library() {
  const [activeTabID, setActiveTabID] = useState(TOTAL_TAB.id);
  const isLogin = useIsLoginValue();
  const navigate = useNavigate();

  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };

  return (
    <MainBox>
      <BoxHeader>
        <LibraryTabs {...{ activeTabID, handleChange }} />
        {/* TODO: 책장 기능 이후에 추가 예정 */}
        {isLogin && (
          <Button
            variant="contained"
            startIcon={<CollectionsBookmarkIcon />}
            onClick={() => console.log("TODO: 책장 추가 모달")}>
            책장 수정하기
          </Button>
        )}
      </BoxHeader>
      {isLogin && <BookList />}
      {isLogin && (
        <Tooltip title="새로운 책을 추가해보세요">
          <AddFab
            color="primary"
            aria-label="add"
            onClick={() => navigate(ROUTE_PATH.newBook)}>
            <LibraryAddIcon />
          </AddFab>
        </Tooltip>
      )}
    </MainBox>
  );
}
