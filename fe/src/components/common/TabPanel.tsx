import { Box } from "@mui/material";
import React from "react";

type TabPanelProps = {
  index: number;
  value: number;
} & React.HTMLAttributes<HTMLDivElement>;

export default function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;
  const isActive = value === index;

  return (
    <Box
      sx={{ width: "100%", gap: "1rem", position: "relative" }}
      role="tabpanel"
      hidden={!isActive}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}>
      {isActive && children}
    </Box>
  );
}
