import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

export const THEME = {
  light: "light" as const,
  dark: "dark" as const,
};

type Theme = keyof typeof THEME;
const defaultTheme: Theme = THEME.light;

const themeAtom = atom<Theme>(defaultTheme);
const useThemeAtom = atom(
  (get) => get(themeAtom),
  (_, set, update: Theme) => set(themeAtom, update)
);

const useThemeValue = () => useAtomValue(themeAtom);
const useSetTheme = () => useSetAtom(useThemeAtom);
const useTheme = () => useAtom(useThemeAtom);

export { useSetTheme, useTheme, useThemeValue };
