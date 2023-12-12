import { TableRow, styled } from "@mui/material";

export const GatheringTableItem = styled(TableRow)(({ theme }) => ({
  "&:hover": {
    backgroundColor: theme.palette.action.hover,
    cursor: "pointer",
  },
}));
