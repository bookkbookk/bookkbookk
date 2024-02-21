import { styled } from "@mui/material";

export const Message = styled("p")`
  font: ${({ theme: { typography } }) => typography.h3};
  font-family: "SOYO Maple Bold";
  color: ${({ theme: { palette } }) => palette.text.primary};
  white-space: pre-wrap;
  text-align: center;
`;

export const Wrapper = styled("div")`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  gap: 2rem;
`;
