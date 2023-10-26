import { Paper } from "@mui/material";
import MuiInputBase from "@mui/material/InputBase";
import { styled } from "@mui/material/styles";

export const Search = styled(Paper)(({ theme }) => ({
  "position": "relative",
  "borderRadius": theme.shape.borderRadius,
  "backgroundColor": theme.palette.background.paper,
  "border": `1px solid ${theme.palette.divider}`,
  "width": "100%",
  "&:hover, &:focus-within ": {
    border: `1px solid ${theme.palette.text.primary}`,
  },
}));

export const SearchIconWrapper = styled("div")(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: "100%",
  position: "absolute",
  pointerEvents: "none",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
}));

export const InputBase = styled(MuiInputBase)(({ theme }) => ({
  "width": "100%",
  "height": "100%",
  "padding": theme.spacing(1, 1, 1, 0),

  "& .MuiInputBase-input": {
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create("width"),
    height: "100%",
  },
}));
