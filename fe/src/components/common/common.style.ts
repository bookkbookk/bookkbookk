import { Badge, Box, styled } from "@mui/material";

export const Container = styled(Box)`
  width: 60vw;
  display: flex;
  flex-direction: column;
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
