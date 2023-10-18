import { alpha } from "@mui/material/styles";
import { THEME_MODE, ThemeMode } from "store/useTheme";

export const grey = {
  0: "#FFFFFF",
  50: "#FEFEFE",
  100: "#F9FAFB",
  200: "#F4F6F8",
  300: "#DFE3E8",
  400: "#C4CDD5",
  500: "#919EAB",
  600: "#637381",
  700: "#454F5B",
  800: "#212B36",
  900: "#161C24",
};

export const secondary = {
  lighter: "#ffe0b2",
  light: "#ffb84d",
  main: "#ff9900",
  dark: "#f57d00",
  darker: "#e65200",
  contrastText: "#FFFFFF",
};

export const primary = {
  lighter: "#b5e5d2",
  light: "#82d4b5",
  main: "#40C498",
  dark: "#00aa6d",
  darker: "#009b62",
  contrastText: "#FFFFFF",
};

export const info = {
  lighter: "#CAFDF5",
  light: "#61F3F3",
  main: "#00B8D9",
  dark: "#006C9C",
  darker: "#003768",
  contrastText: "#FFFFFF",
};

export const success = {
  lighter: "#C8FAD6",
  light: "#5BE49B",
  main: "#00A76F",
  dark: "#007867",
  darker: "#004B50",
  contrastText: "#FFFFFF",
};

export const warning = {
  lighter: "#FFF5CC",
  light: "#FFD666",
  main: "#FFAB00",
  dark: "#B76E00",
  darker: "#7A4100",
  contrastText: grey[800],
};

export const error = {
  lighter: "#FFE9D5",
  light: "#FFAC82",
  main: "#FF5630",
  dark: "#B71D18",
  darker: "#7A0916",
  contrastText: "#FFFFFF",
};

export const common = {
  black: "#000000",
  white: "#FFFFFF",
};

export const action = {
  hover: alpha(grey[500], 0.08),
  selected: alpha(grey[500], 0.16),
  disabled: alpha(grey[500], 0.8),
  disabledBackground: alpha(grey[500], 0.24),
  focus: alpha(grey[500], 0.24),
  hoverOpacity: 0.08,
  disabledOpacity: 0.48,
};

const base = {
  primary,
  secondary,
  info,
  success,
  warning,
  error,
  grey,
  common,
  divider: alpha(grey[500], 0.2),
  action,
};

const dark = {
  text: {
    primary: grey[50],
    secondary: grey[200],
    disabled: grey[300],
  },
  background: {
    paper: grey[800],
    default: grey[900],
    neutral: grey[800],
  },
  action: {
    ...base.action,
    active: grey[200],
  },
};

const light = {
  text: {
    primary: grey[800],
    secondary: grey[600],
    disabled: grey[500],
  },
  background: {
    paper: common.white,
    default: grey[100],
    neutral: grey[200],
  },
  action: {
    ...base.action,
    active: grey[600],
  },
};

export const getPalette = (mode: ThemeMode) => ({
  ...base,
  mode,
  ...(mode === THEME_MODE.dark ? dark : light),
});
