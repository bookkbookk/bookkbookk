import { a11yProps } from "@components/utils";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";
import { BOOK_CLUB_TAB } from "../constants";

export default function BookClubTabs({
  activeTabID,
  handleChange,
}: {
  activeTabID: number;
  handleChange: (event: React.SyntheticEvent, newValue: number) => void;
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
        {BOOK_CLUB_TAB.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
      </Tabs>
    </Box>
  );
}
