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
  alignItems: "center",
  gap: theme.spacing(2),
}));

export const RightBox = styled(Box)(({ theme }) => ({
  display: "flex",
  width: "30%",
  height: "100%",
  backgroundColor: theme.palette.background.paper,
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
  gap: theme.spacing(2),
}));

export const BoxContent = styled(Box)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  width: "80%",
  height: "100%",
  gap: "2rem",
  margin: theme.spacing(4),
}));

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
  color: ${({ theme }) => theme.palette.text.secondary};
  white-space: pre-wrap;
  text-align: center;
`;