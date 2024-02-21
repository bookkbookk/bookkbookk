import { BOOK_CHAPTER_IDS } from "@components/constants";
import { a11yProps } from "@components/utils";
import { Box, Tabs as MuiTabs, Tab } from "@mui/material";
import React from "react";

type TabList = { id: number; label: string }[];

export default function Tabs({
  activeTabID,
  handleChange,
  tabList,
}: {
  activeTabID: number;
  handleChange: (
    event: React.SyntheticEvent,
    newValue: BOOK_CHAPTER_IDS
  ) => void;
  tabList: TabList;
}) {
  return (
    <Box
      sx={{
        borderBottom: 1,
        borderColor: "divider",
      }}>
      <MuiTabs
        value={activeTabID}
        onChange={handleChange}
        aria-label="book club list tabs">
        {tabList.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
      </MuiTabs>
    </Box>
  );
}
