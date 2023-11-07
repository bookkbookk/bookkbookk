import {
  Badge,
  Box,
  Button,
  Fab,
  Card as MuiCard,
  ListItemButton as MuiListItemButton,
  Typography,
  styled,
} from "@mui/material";
import { HEADER } from "layout/constants";

export const Container = styled(Box)`
  width: 60vw;
  display: flex;
  flex-direction: column;
`;

export const Wrapper = styled(Box)`
  display: flex;
  color: ${({ theme }) => theme.palette.text.primary};
  gap: ${({ theme }) => theme.spacing(2)};
`;

export const PlusBadge = styled(Badge)(({ theme }) => ({
  "& .MuiBadge-badge": {
    bottom: 20,
    right: 15,
    width: 40,
    height: 40,
    borderRadius: "50%",
    border: `3px solid ${theme.palette.background.paper}`,
  },
}));

export const MainBox = styled(Box)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
  padding: theme.spacing(2, 4),
  alignItems: "center",
}));

export const BoxHeader = styled(Box)(({ theme }) => ({
  display: "flex",
  width: "100%",
  justifyContent: "space-between",
  gap: theme.spacing(2),
}));

export const LeftBox = styled(Box)(({ theme }) => ({
  display: "flex",
  height: "100%",
  width: `calc(70% - ${theme.spacing(2)})`,
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
  gap: theme.spacing(2),
}));

export const RightBox = styled(Box)(({ theme }) => ({
  position: "fixed",
  right: 0,
  top: `${HEADER.height}px`,
  display: "flex",
  width: "30%",
  height: "100%",
  backgroundColor: theme.palette.background.paper,
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
  gap: theme.spacing(2),
  boxShadow: theme.shadows[1],
}));

export const BoxContent = styled(Box)({
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  width: "80%",
  height: "100%",
  gap: "2rem",
  padding: "2rem 0",
});

export const Section = styled("section")`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: ${({ theme }) => theme.spacing(6)};
`;

export const SectionContent = styled("div")`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing(2)};
`;

export const SectionTitle = styled("h2")`
  color: ${({ theme }) => theme.palette.text.primary};
  font: ${({ theme }) => theme.typography.h2};
  font-family: "SOYO Maple Bold";
`;

export const SectionDescription = styled("p")`
  font: ${({ theme }) => theme.typography.body1};
  color: ${({ theme }) => theme.palette.text.primary};
  white-space: pre-wrap;
  text-align: center;
`;

export const Card = styled(MuiCard)(({ theme }) => ({
  width: "100%",
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  gap: theme.spacing(2),
  padding: theme.spacing(3),
}));

export const CardContent = styled(Box)(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  gap: theme.spacing(2),
}));

export const ButtonWrapper = styled(Box)(({ theme }) => ({
  display: "flex",
  justifyContent: "space-between",
  width: "100%",
  marginTop: theme.spacing(2),
}));

export const RoundButton = styled(Button)(({ theme }) => ({
  width: "100%",
  borderRadius: theme.spacing(4),
  backgroundColor: theme.palette.background.paper,
  color: theme.palette.primary.main,
  border: `1px solid ${theme.palette.divider}`,
}));

export const AddFab = styled(Fab)(({ theme }) => ({
  position: "fixed",
  bottom: theme.spacing(6),
  right: theme.spacing(6),
}));

export const ModalBox = styled(Box)(({ theme }) => ({
  position: "absolute",
  maxWidth: "40vw",
  minWidth: "40vw",
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
  minWidth: "90px",
  maxWidth: "90px",
  minHeight: "120px",
  maxHeight: "120px",
});

export const BookDescription = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  maxWidth: "100%",
  maxHeight: theme.spacing(8),
  overflow: "hidden",
  wordBreak: "keep-all",
  textOverflow: "ellipsis",
  WebkitLineClamp: 3,
}));

export const Target = styled("div")({
  height: 1,
});
