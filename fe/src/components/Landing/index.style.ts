import { styled } from "@mui/material";
import { Link } from "react-router-dom";

export const Container = styled("div")`
  display: flex;
  flex-direction: column;
  width: 60vw;
`;

export const Header = styled("header")`
  display: flex;
  color: ${({ theme }) => theme.palette.primary.contrastText};
  padding: ${({ theme }) => theme.spacing(3)} 0;
  justify-content: space-between;
`;

export const LogoContainer = styled(Link)`
  display: flex;
  align-items: center;
`;

export const Section = styled("section")`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  gap: ${({ theme }) => theme.spacing(6)};
`;

export const SectionContent = styled("div")`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing(2)};
`;

export const SectionTitle = styled("h2")`
  font-size: 2rem;
  color: ${({ theme }) => theme.palette.text.primary};
  font: ${({ theme }) => theme.typography.h2};
  font: ${({ theme }) => theme.typography.fontFamily};
`;

export const SectionDescription = styled("p")`
  font-size: 1rem;
  color: ${({ theme }) => theme.palette.text.secondary};
`;

export const LogoTitle = styled("h1")`
  color: ${({ theme }) => theme.palette.text.primary};
  font: ${({ theme }) => theme.typography.h4};
  padding-left: ${({ theme }) => theme.spacing(0.5)};
`;

export const Wrapper = styled("div")`
  display: flex;
  color: ${({ theme }) => theme.palette.text.primary};
  gap: ${({ theme }) => theme.spacing(2)};
`;

export const ButtonText = styled("span")`
  color: ${({ theme }) => theme.palette.text.primary};
  padding-left: ${({ theme }) => theme.spacing(1)};
`;
