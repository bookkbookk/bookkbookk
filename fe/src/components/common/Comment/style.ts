import { styled } from "@mui/material";

export const CommentContainer = styled("div")(({ theme }) => ({
  width: "100%",
  borderRadius: theme.spacing(2),
  border: `1px solid ${theme.palette.divider}`,
  backgroundColor: theme.palette.background.paper,
}));

export const CommentHeader = styled("div")(({ theme }) => ({
  width: "100%",
  height: theme.spacing(8),
  padding: theme.spacing(1, 2),

  display: "flex",
  alignItems: "center",
  justifyContent: "space-between",
  borderBottom: `1px solid ${theme.palette.divider}`,
  backgroundColor: theme.palette.background.neutral,
  borderTopLeftRadius: theme.spacing(2),
  borderTopRightRadius: theme.spacing(2),
}));

export const CommentBody = styled("div")(({ theme }) => ({
  width: "100%",
  paddingTop: theme.spacing(2),
  paddingLeft: theme.spacing(2),
  paddingRight: theme.spacing(2),
  borderBottomLeftRadius: theme.spacing(2),
  borderBottomRightRadius: theme.spacing(2),
  color: theme.palette.text.primary,
}));
