import { Badge, styled } from "@mui/material";

export const Container = styled("div")`
  display: flex;
  flex-direction: column;
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

export const Wrapper = styled("div")`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  gap: 2rem;
  padding: 2rem 0;
`;
