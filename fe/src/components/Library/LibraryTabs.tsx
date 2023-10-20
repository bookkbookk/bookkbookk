import { a11yProps } from "@components/utils";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";
import { DEFAULT_TABS, LIBRARY_TABS, TOTAL_TAB } from "../constants";

export default function LibraryTabs({
  activeTabID,
  handleChange,
}: {
  activeTabID: number;
  handleChange: (event: React.SyntheticEvent, newValue: number) => void;
}) {
  return (
    <Box sx={{ maxWidth: "80%" }}>
      <Tabs
        sx={{
          "& .MuiTabs-scrollButtons.Mui-disabled": {
            opacity: 0.3,
          },
        }}
        variant="scrollable"
        scrollButtons={true}
        value={activeTabID}
        onChange={handleChange}
        aria-label="library tab list">
        <Tab
          key={TOTAL_TAB.id}
          label={TOTAL_TAB.label}
          {...a11yProps(TOTAL_TAB.id)}
        />
        {/* TODO: DB에 기본으로 추가 요청 */}
        {DEFAULT_TABS.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
        {/* TODO: 책장 목록 조회 */}
        {LIBRARY_TABS.map((tab) => (
          <Tab key={tab.id} label={tab.label} {...a11yProps(tab.id)} />
        ))}
      </Tabs>
    </Box>
  );
}
