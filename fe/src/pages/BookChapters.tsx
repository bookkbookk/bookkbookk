import HeaderButtons from "@components/BookChapters/NewBookChapters/HeaderButtons";
import NewBookChapters from "@components/BookChapters/NewBookChapters/NewBookChapters";
import Tabs from "@components/common/Tabs";
import { BoxHeader, MainBox } from "@components/common/common.style";
import { BOOK_CHAPTERS_TAB } from "@components/constants";
import AddIcon from "@mui/icons-material/Add";
import { Button } from "@mui/material";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { useIsLoginValue } from "store/useMember";

export default function BookChapters() {
  const { state } = useLocation();
  const [isViewerMode, setIsViewerMode] = useState(!state.isChapterAddMode);
  const [activeTabID, setActiveTabID] = useState(BOOK_CHAPTERS_TAB[0].id);
  const isLogin = useIsLoginValue();

  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };
  const onChangeChapterViewer = () => setIsViewerMode(true);

  return (
    <MainBox>
      <BoxHeader>
        {isViewerMode ? (
          <>
            <Tabs
              {...{ activeTabID, handleChange, tabList: BOOK_CHAPTERS_TAB }}
            />
            {isLogin && (
              <Button
                variant="contained"
                endIcon={<AddIcon />}
                onClick={() => setIsViewerMode(false)}>
                새로운 챕터 추가하기
              </Button>
            )}
          </>
        ) : (
          <HeaderButtons {...{ onChangeChapterViewer }} />
        )}
      </BoxHeader>
      {!isViewerMode && <NewBookChapters />}
    </MainBox>
  );
}
