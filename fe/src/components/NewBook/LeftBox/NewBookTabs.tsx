import { a11yProps } from "@components/utils";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";
import { useActiveTab } from "store/useNewBook";
import { NEW_BOOK_TABS } from "../../constants";

export default function NewBookTabs() {
  const [activeTabID, setActiveTabID] = useActiveTab();
  const handleChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTabID({ type: "INDEX", payload: newValue });
  };

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
        {NEW_BOOK_TABS.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
      </Tabs>
    </Box>
  );
}
