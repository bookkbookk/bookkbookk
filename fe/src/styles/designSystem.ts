export type FontName = keyof typeof font;
export type ColorName = keyof typeof color;
export type Radius = keyof typeof radius;
export type Opacity = keyof typeof opacity;

const color = {
  white: "#FFF",
  grey50: "#FEFEFE",
  grey100: "#F7F7FC",
  grey200: "#EFF0F6",
  grey300: "#D9DBE9",
  grey400: "#BEC1D5",
  grey500: "#A0A3BD",
  grey600: "#6E7191",
  grey700: "#4E4B66",
  grey800: "#2A2A44",
  grey900: "#14142B",
  black: "#000",
  blue: "#007AFF",
  navy: "#0025E6",
  red: "#FF3B30",
  orange: "#FF7900",
};

const radius = {
  m: "12px",
  l: "16px",
  half: "50%",
};

const font = {
  displayBold32: "700 32px Roboto, sans-serif",
  displayBold24: "700 24px Roboto, sans-serif",
  displayBold20: "700 20px Roboto, sans-serif",
  displayBold16: "700 16px Roboto, sans-serif",
  displayBold14: "700 14px Roboto, sans-serif",
  displayBold12: "700 12px Roboto, sans-serif",

  displayMD20: "500 20px Roboto, sans-serif",
  displayMD16: "500 16px Roboto, sans-serif",
  displayMD12: "500 12px Roboto, sans-serif",

  selectedBold20: "700 20px Roboto, sans-serif",
  selectedBold16: "700 16px Roboto, sans-serif",
  selectedBold12: "700 12px Roboto, sans-serif",

  availableMD20: "500 20px Roboto, sans-serif",
  availableMD16: "500 16px Roboto, sans-serif",
  availableMD12: "500 12px Roboto, sans-serif",

  brandBold32: "700 32px SOYO Maple Bold, sans-serif",
};

const opacity = {
  hover: 0.8,
  press: 0.64,
  disabled: 0.32,
  transparent: 0.04,
};

export const lightMode = {
  palette: {
    neutralTextWeak: color.grey600,
    neutralTextDefault: color.grey700,
    neutralTextStrong: color.grey900,
    neutralSurfaceDefault: color.grey100,
    neutralSurfaceBold: color.grey200,
    neutralSurfaceStrong: color.grey50,
    neutralBorderDefault: color.grey300,
    neutralBorderDefaultActive: color.grey900,
    brandTextWeak: color.blue,
    brandTextDefault: color.grey50,
    brandSurfaceWeak: color.grey50,
    brandSurfaceDefault: color.blue,
    brandBorderDefault: color.blue,
    dangerTextDefault: color.red,
    dangerBorderDefault: color.red,
    boxShadow: "#14142B0A",
  },
  color,
  radius,
  opacity,
  font,
  filter: {},
};

export const darkMode = {
  palette: {
    neutralTextWeak: color.grey500,
    neutralTextDefault: color.grey400,
    neutralTextStrong: color.grey50,
    neutralSurfaceDefault: color.grey900,
    neutralSurfaceBold: color.grey700,
    neutralSurfaceStrong: color.grey800,
    neutralBorderDefault: color.grey600,
    neutralBorderDefaultActive: color.grey300,
    brandTextWeak: color.blue,
    brandTextDefault: color.grey50,
    brandSurfaceWeak: color.grey900,
    brandSurfaceDefault: color.blue,
    brandBorderDefault: color.blue,
    dangerTextDefault: color.red,
    dangerBorderDefault: color.red,
    boxShadow: "#14142BCC",
  },
  color,
  radius,
  opacity,
  font,
  filter: {},
};

export const designSystem = {
  light: lightMode,
  dark: darkMode,
};
