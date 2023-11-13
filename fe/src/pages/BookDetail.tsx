import BookChapterList from "@components/BookDetail/BookChapterList";
import NewBookChapters from "@components/BookDetail/NewBookChapters/NewBookChapters";
import { AddFab } from "@components/common/common.style";
import AddIcon from "@mui/icons-material/Add";
import { Tooltip } from "@mui/material";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { useIsLoginValue } from "store/useMember";

export default function BookDetail() {
  const { state } = useLocation();
  const [isViewerMode, setIsViewerMode] = useState(!state.isChapterAddMode);

  const isLogin = useIsLoginValue();
  const onChangeChapterViewer = () => setIsViewerMode(true);

  return (
    <>
      {!isViewerMode && <NewBookChapters {...{ onChangeChapterViewer }} />}
      {isViewerMode && <BookChapterList />}
      {isLogin && isViewerMode && (
        <Tooltip title="새로운 챕터를 추가해보세요">
          <AddFab
            color="primary"
            aria-label="add"
            onClick={() => setIsViewerMode(false)}>
            <AddIcon />
          </AddFab>
        </Tooltip>
      )}
    </>
  );
}
