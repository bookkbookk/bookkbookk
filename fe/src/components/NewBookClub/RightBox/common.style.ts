import { Card as MuiCard, styled } from "@mui/material";

export const Card = styled(MuiCard)(({ theme }) => ({
  width: "100%",
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  gap: theme.spacing(2),
  padding: theme.spacing(3),
}));
