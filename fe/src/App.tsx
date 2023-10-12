import { queryClient } from "@api/queryClient";
import GlobalStyle from "@styles/GlobalStyle";
import { designSystem } from "@styles/designSystem";
import { QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { RouterProvider } from "react-router-dom";
import { router } from "router/router";
import { useThemeValue } from "store/useTheme";
import { ThemeProvider } from "styled-components";
import * as S from "./App.style";

export default function App() {
  const theme = useThemeValue();
  const currentTheme = designSystem[theme];

  return (
    <ThemeProvider theme={currentTheme}>
      <GlobalStyle />
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={false} />
        <S.App>
          <RouterProvider router={router} />
        </S.App>
      </QueryClientProvider>
      <S.ModalRoot id="modal-root" />
      {/* <Toaster /> */}
    </ThemeProvider>
  );
}
