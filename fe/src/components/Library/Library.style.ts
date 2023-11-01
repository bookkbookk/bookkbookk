import { Card, Fab, styled } from "@mui/material";

export const BookAddFab = styled(Fab)(({ theme }) => ({
  position: "fixed",
  bottom: theme.spacing(6),
  right: theme.spacing(6),
}));

export const BookCard = styled(Card)({
  "transition": "all 0.2s ease-in-out",
  "&:hover": {
    cursor: "pointer",
    transform: "scale(1.05)",
  },
});
