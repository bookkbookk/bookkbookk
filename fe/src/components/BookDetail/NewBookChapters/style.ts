import { TextField, styled } from "@mui/material";

export const ChapterTextField = styled(TextField)(({ theme }) => ({
  "width": "100%",
  "& .MuiInputBase-root": {
    padding: theme.spacing(1, 0),
  },
  "& .MuiInputBase-input": {
    fontSize: "1.5rem",
    fontWeight: "bold",
  },
}));
