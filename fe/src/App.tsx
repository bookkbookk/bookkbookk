import { queryClient } from "@api/queryClient";
import { Global, ThemeProvider } from "@emotion/react";
import {
  ThemeProvider as MUIThemeProvider,
  THEME_ID,
  createTheme,
} from "@mui/material/styles";
import { GlobalStyle } from "@styles/GlobalStyle";
import { designSystem } from "@styles/designSystem";
import { QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { RouterProvider } from "react-router-dom";
import { router } from "router/router";
import { useThemeValue } from "store/useTheme";
import * as S from "./App.style";

export default function App() {
  const theme = useThemeValue();
  const currentTheme = designSystem[theme];

  return (
    <ThemeProvider theme={currentTheme}>
      <Global styles={GlobalStyle} />
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={false} />
        <S.App>
          <MUIThemeProvider theme={{ [THEME_ID]: createTheme() }}>
            <RouterProvider router={router} />
          </MUIThemeProvider>
        </S.App>
      </QueryClientProvider>
      <S.ModalRoot id="modal-root" />
      {/* <Toaster /> */}
    </ThemeProvider>
  );
}
