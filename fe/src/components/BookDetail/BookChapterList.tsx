import { BookChapterTabID } from "@api/chapters/type";
import Tabs from "@components/common/Tabs";
import { BoxHeader, LeftBox, RightBox } from "@components/common/common.style";
import { BOOK_CHAPTERS_TAB } from "@components/constants";
import { Box } from "@mui/material";
import React, { useState } from "react";
import BookChapterListPanel from "./BookChapterListPanel/BookChapterListPanel";

export default function BookChapterList() {
  const [activeTabID, setActiveTabID] = useState<BookChapterTabID["id"]>(
    BOOK_CHAPTERS_TAB[0].id
  );

  const handleChange = (
    _: React.SyntheticEvent,
    newValue: BookChapterTabID["id"]
  ) => {
    setActiveTabID(newValue);
  };

  return (
    <Box
      sx={{
        display: "flex",
        height: "100%",
        padding: "1rem 2rem",
        flexDirection: "column",
      }}>
      <BoxHeader>
        <Tabs {...{ activeTabID, handleChange, tabList: BOOK_CHAPTERS_TAB }} />
      </BoxHeader>
      <LeftBox>
        <BookChapterListPanel statusId={activeTabID} />
      </LeftBox>
      <RightBox>책 정보, 책 상태 변경, 책 삭제, 멤버 독서율</RightBox>
    </Box>
  );
}
