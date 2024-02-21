import Tabs from "@components/common/Tabs";
import {
  BoxContent,
  BoxHeader,
  LeftBox,
  RightBox,
} from "@components/common/common.style";
import { BOOK_CHAPTERS_TAB, BOOK_CHAPTER_TABS } from "@components/constants";
import Box from "@mui/material/Box";
import React, { useState } from "react";
import BookChapterListPanel from "./BookChapterListPanel/BookChapterListPanel";
import ChapterBookCard from "./ChapterBookCard";

export default function BookChapterList() {
  const [activeTabID, setActiveTabID] = useState<number>(
    BOOK_CHAPTER_TABS.ALL.id
  );

  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
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
      <RightBox>
        <BoxContent>
          <ChapterBookCard />
        </BoxContent>
      </RightBox>
    </Box>
  );
}
