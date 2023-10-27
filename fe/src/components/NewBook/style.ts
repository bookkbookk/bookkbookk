import {
  Box,
  ListItemButton as MuiListItemButton,
  Typography,
  styled,
} from "@mui/material";
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

export const BookDescription = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  maxWidth: "100%",
  maxHeight: theme.spacing(8),
  overflow: "hidden",
  wordBreak: "keep-all",
  textOverflow: "ellipsis",
  WebkitLineClamp: 3,
}));

export const ModalBox = styled(Box)(({ theme }) => ({
  position: "absolute",
  maxWidth: "30vw",
  minWidth: "30vw",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  backgroundColor: theme.palette.background.paper,
  border: `1px solid ${theme.palette.divider}`,
  borderRadius: theme.spacing(2),
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
  padding: theme.spacing(4),
  gap: theme.spacing(2),
}));

export const ModalHeader = styled(Box)({
  display: "flex",
  alignItems: "center",
  justifyContent: "space-between",
  width: "100%",
});

export const ModalBody = styled(Box)({
  display: "flex",
  width: "100%",
  maxHeight: "70vh",
  overflow: "scroll",
});

export const ImageWrapper = styled(Box)(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  backgroundColor: theme.palette.background.default,
  width: "100%",
  borderRadius: theme.spacing(1),
  padding: theme.spacing(4),
}));

export const BookCoverImage = styled("img")({
  objectFit: "fill",
});

export const BookLink = styled(Link)(({ theme }) => ({
  "color": theme.palette.text.primary,
  "fontFamily": "SOYO Maple Bold",

  "&:hover": {
    textDecoration: `underline ${theme.palette.secondary.light}`,
  },
}));

export const ListItemButton = styled(MuiListItemButton)(({ theme }) => ({
  "width": "100%",
  "display": "flex",
  "alignItems": "center",
  "borderRadius": theme.spacing(1),
  "gap": theme.spacing(2),
  "&:hover": {
    backgroundColor: theme.palette.action.hover,
  },
}));
