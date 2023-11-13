import { BookChapterStatus } from "@api/chapters/type";
import { BOOK_CHAPTERS_TAB } from "@components/constants";
import { a11yProps } from "@components/utils";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";

export default function BookClubTabs({
  activeTabID,
  handleChange,
  tabList,
}: {
  activeTabID: BookChapterStatus["id"];
  handleChange: (
    event: React.SyntheticEvent,
    newValue: BookChapterStatus["id"]
  ) => void;
  tabList: typeof BOOK_CHAPTERS_TAB;
}) {
  return (
    <Box
      sx={{
        borderBottom: 1,
        borderColor: "divider",
      }}>
      <Tabs
        value={activeTabID}
        onChange={handleChange}
        aria-label="book club list tabs">
        {tabList.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
      </Tabs>
    </Box>
  );
}
