import { Box, Typography, styled } from "@mui/material";
import { Link } from "react-router-dom";

export const InfoWrapper = styled("div")(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  maxWidth: `calc(100% - ${theme.spacing(8)})`,
  padding: theme.spacing(2),
}));

export const SubInfoWrapper = styled("div")(({ theme }) => ({
  "display": "flex",
  "alignItems": "center",
  "width": "100%",
  "& > *:not(:last-child)::after": {
    content: "'·'",
    margin: theme.spacing(0, 1),
  },
}));

export const Title = styled(Typography)({
  overflow: "hidden",
  whiteSpace: "nowrap",
  textOverflow: "ellipsis",
  wordBreak: "break-all",
});

export const BookSubInfo = styled(Box)(({ theme }) => ({
  maxWidth: "100%",
  overflow: "hidden",
  whiteSpace: "nowrap",
  textOverflow: "ellipsis",
  color: theme.palette.text.primary,
}));

export const BookLink = styled(Link)(({ theme }) => ({
  "color": theme.palette.text.primary,
  "fontFamily": "SOYO Maple Bold",

  "&:hover": {
    textDecoration: `underline ${theme.palette.secondary.light}`,
  },
}));
