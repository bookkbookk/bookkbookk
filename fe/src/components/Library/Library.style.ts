import { Fab, styled } from "@mui/material";

export const BookAddFab = styled(Fab)(({ theme }) => ({
  position: "fixed",
  bottom: theme.spacing(6),
  right: theme.spacing(6),
}));
