import { Badge, styled } from "@mui/material";

export const Container = styled("div")`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 60vw;
  height: 100vh;
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
