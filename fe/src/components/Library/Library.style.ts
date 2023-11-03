import { Card, styled } from "@mui/material";

export const BookCard = styled(Card)({
  "transition": "all 0.2s ease-in-out",
  "&:hover": {
    cursor: "pointer",
    transform: "scale(1.05)",
  },
});
