import { Stack, styled } from "@mui/material";
import React from "react";

export default function CommentTextareaWrapper({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <CommentTextareaWrapperStyle width="100%">
      {children}
    </CommentTextareaWrapperStyle>
  );
}

const CommentTextareaWrapperStyle = styled(Stack)(({ theme }) => ({
  borderRadius: theme.spacing(1),
  padding: theme.spacing(1, 0),
  border: `1px solid ${theme.palette.divider}`,
  backgroundColor: theme.palette.background.paper,
}));
