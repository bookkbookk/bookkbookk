import { THEME_MODE, useThemeMode } from "store/useTheme";
import * as S from "./ThemeSwitch.style";

export default function ThemeSwitch() {
  const [themeMode, toggleThemeMode] = useThemeMode();
  const isDarkMode = themeMode === THEME_MODE.dark;

  return <S.ThemeSwitch checked={isDarkMode} onClick={toggleThemeMode} />;
}
