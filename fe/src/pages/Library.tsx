import * as S from "@components/Library/Library.style";
import LibraryTabs from "@components/Library/LibraryTabs";
import { BoxHeader, MainBox } from "@components/common/common.style";
import { TOTAL_TAB } from "@components/constants";
import CollectionsBookmarkIcon from "@mui/icons-material/CollectionsBookmark";
import LibraryAddIcon from "@mui/icons-material/LibraryAdd";
import { Button, Tooltip } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function Library() {
  const [activeTabID, setActiveTabID] = useState(TOTAL_TAB.id);
  const isLogin = useIsLoginValue();
  const navigate = useNavigate();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
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
      <Tooltip title="새로운 책을 추가해보세요">
        <S.BookAddFab
          color="primary"
          aria-label="add"
          onClick={() => navigate(ROUTE_PATH.newBook)}>
          <LibraryAddIcon />
        </S.BookAddFab>
      </Tooltip>
    </MainBox>
  );
}
