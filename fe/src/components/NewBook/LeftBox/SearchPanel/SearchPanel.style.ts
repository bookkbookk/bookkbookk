import { ListItemButton, Typography, styled } from "@mui/material";

export const SearchBookListItem = styled(ListItemButton)(({ theme }) => ({
  "width": "100%",
  "display": "flex",
  "alignItems": "center",
  "padding": theme.spacing(1, 2),
  "borderRadius": theme.spacing(1),
  "&:hover": {
    backgroundColor: theme.palette.action.hover,
  },
}));

export const BookCover = styled("img")(({ theme }) => ({
  maxWidth: theme.spacing(10),
  maxHeight: theme.spacing(16),
  objectFit: "cover",
  borderRadius: theme.spacing(1),
}));

export const BookInfo = styled("div")(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  maxWidth: `calc(100% - ${theme.spacing(8)})`,
  padding: theme.spacing(2),
  marginLeft: theme.spacing(2),
}));

export const BookSubInfoWrapper = styled("div")(({ theme }) => ({
  "display": "flex",
  "alignItems": "center",
  "width": "100%",
  "& > *:not(:last-child)::after": {
    content: "'·'",
    margin: theme.spacing(0, 1),
  },
}));

export const BookTitle = styled(Typography)(({ theme }) => ({
  fontWeight: theme.typography.fontWeightBold,
  overflow: "hidden",
  whiteSpace: "nowrap",
  textOverflow: "ellipsis",
  wordBreak: "break-all",
}));

export const BookSubInfo = styled(Typography)(({ theme }) => ({
  maxWidth: "100%",
  overflow: "hidden",
  whiteSpace: "nowrap",
  textOverflow: "ellipsis",
  color: theme.palette.text.primary,
}));

export const BookDescription = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  maxWidth: "100%",
  maxHeight: theme.spacing(8),
  overflow: "hidden",
  wordBreak: "keep-all",
  textOverflow: "ellipsis",
  WebkitLineClamp: 3,
}));
