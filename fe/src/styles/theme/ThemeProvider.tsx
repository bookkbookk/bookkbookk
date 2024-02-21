import { CssBaseline } from "@mui/material";
import {
  ThemeProvider as MUIThemeProvider,
  createTheme,
} from "@mui/material/styles";
import { useMemo } from "react";
import { useThemeModeValue } from "store/useTheme";
import { getPalette } from "./palette";
import { components, typography } from "./typography";

export default function ThemeProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const themeMode = useThemeModeValue();
  const theme = useMemo(() => {
    return createTheme({
      palette: getPalette(themeMode),
      typography,
      components,
    });
  }, [themeMode]);

  return (
    <MUIThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </MUIThemeProvider>
  );
}
