import { a11yProps } from "@components/utils";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";

export default function BookClubTabs({
  activeTabID,
  handleChange,
  tabList,
}: {
  activeTabID: number;
  handleChange: (event: React.SyntheticEvent, newValue: number) => void;
  tabList: { id: number; label: string }[];
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
