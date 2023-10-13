import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

export const THEME_MODE = {
  light: "light" as const,
  dark: "dark" as const,
};

export type ThemeMode = keyof typeof THEME_MODE;
const defaultThemeMode: ThemeMode = window.matchMedia(
  "(prefers-color-scheme: dark)"
).matches
  ? THEME_MODE.dark
  : THEME_MODE.light;

const themeModeAtom = atom<ThemeMode>(defaultThemeMode);
const useThemeModeAtom = atom(
  (get) => get(themeModeAtom),
  (_, set) => {
    set(themeModeAtom, (prev) =>
      prev === THEME_MODE.light ? THEME_MODE.dark : THEME_MODE.light
    );
  }
);

const useThemeModeValue = () => useAtomValue(themeModeAtom);
const useToggleThemeMode = () => useSetAtom(useThemeModeAtom);
const useThemeMode = () => useAtom(useThemeModeAtom);

export { useThemeMode, useThemeModeValue, useToggleThemeMode };
