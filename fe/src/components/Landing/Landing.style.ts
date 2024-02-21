import { styled } from "@mui/material";
import { Link } from "react-router-dom";

export const Header = styled("header")`
  display: flex;
  width: 100%;
  color: ${({ theme }) => theme.palette.primary.contrastText};
  padding: ${({ theme }) => theme.spacing(3)} 0;
  justify-content: space-between;
`;

export const LogoContainer = styled(Link)`
  display: flex;
  align-items: center;
`;

export const LogoTitle = styled("h1")`
  color: ${({ theme }) => theme.palette.text.primary};
  font: ${({ theme }) => theme.typography.h4};
  font-family: "SOYO Maple Bold";
  padding-left: ${({ theme }) => theme.spacing(0.5)};
`;

export const ButtonText = styled("span")`
  color: ${({ theme }) => theme.palette.text.primary};
  padding-left: ${({ theme }) => theme.spacing(1)};
`;
