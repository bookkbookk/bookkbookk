import { Badge, Box, styled } from "@mui/material";

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

export const LeftBox = styled(Box)(({ theme }) => ({
  display: "flex",
  width: "70%",
  height: "100%",
  flexDirection: "column",
  justifyContent: "center",
  gap: theme.spacing(2),
}));

export const RightBox = styled(Box)(({ theme }) => ({
  display: "flex",
  width: "30%",
  height: "100%",
  backgroundColor: theme.palette.background.paper,
  flexDirection: "column",
  justifyContent: "center",
  gap: theme.spacing(2),
}));
