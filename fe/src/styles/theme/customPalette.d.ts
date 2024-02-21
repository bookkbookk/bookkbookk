import "@mui/material/styles";

declare module "@mui/material/styles" {
  interface Palette {
    background: Palette["background"];
  }
  interface PaletteOptions {
    background: PaletteOptions["background"];
  }
}

declare module "@mui/material/styles/createPalette" {
  interface TypeBackground {
    default: string;
    paper: string;
    neutral: string;
  }
}
