import { queryClient } from "@api/queryClient";
import { Global } from "@emotion/react";
import { GlobalStyle } from "@styles/GlobalStyle";
import ThemeProvider from "@styles/theme/ThemeProvider";
import { QueryClientProvider } from "@tanstack/react-query";
import { SnackbarProvider } from "notistack";
import { RouterProvider } from "react-router-dom";
import { router } from "routes/router";
import * as S from "./App.style";

export default function App() {
  return (
    <ThemeProvider>
      <Global styles={GlobalStyle} />
      <QueryClientProvider client={queryClient}>
        <SnackbarProvider maxSnack={1} autoHideDuration={2000}>
          <S.App>
            <RouterProvider router={router} />
          </S.App>
        </SnackbarProvider>
      </QueryClientProvider>
    </ThemeProvider>
  );
}
