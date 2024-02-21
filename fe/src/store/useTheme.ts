import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

export const THEME_MODE = {
  light: "light" as const,
  dark: "dark" as const,
};

export type ThemeMode = keyof typeof THEME_MODE;
const cachedThemeMode = localStorage.getItem("themeMode") as ThemeMode;
const userPrefersDark = window.matchMedia(
  "(prefers-color-scheme: dark)"
).matches;

const defaultThemeMode: ThemeMode =
  cachedThemeMode ?? (userPrefersDark ? THEME_MODE.dark : THEME_MODE.light);

const themeModeAtom = atom<ThemeMode>(defaultThemeMode);
const useThemeModeAtom = atom(
  (get) => get(themeModeAtom),
  (_, set) => {
    set(themeModeAtom, (prev) => {
      const isLightMode = prev === THEME_MODE.light;
      localStorage.setItem(
        "themeMode",
        isLightMode ? THEME_MODE.dark : THEME_MODE.light
      );

      return isLightMode ? THEME_MODE.dark : THEME_MODE.light;
    });
  }
);

const useThemeModeValue = () => useAtomValue(useThemeModeAtom);
const useToggleThemeMode = () => useSetAtom(useThemeModeAtom);
const useThemeMode = () => useAtom(useThemeModeAtom);

export { useThemeMode, useThemeModeValue, useToggleThemeMode };
