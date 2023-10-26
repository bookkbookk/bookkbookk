import { ReactComponent as LogoIcon } from "@assets/images/brand.svg";
import AccountPopover from "@components/common/AccountPopover/AccountPopover";
import { GoogleLoginButton } from "@components/common/GoogleLoginButton";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Wrapper } from "@components/common/common.style";
import { PAGE_TITLE } from "@constant/index";
import { IconButton, Toolbar, Typography } from "@mui/material";
import { useLocation } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";
import { AppBar } from "./Layout.style";

export function Header({
  isSideOpen,
  toggleSidebar,
}: {
  isSideOpen: boolean;
  toggleSidebar: () => void;
}) {
  const { pathname } = useLocation();
  const isLogin = useIsLoginValue();

  // TODO: 책 상세 페이지에서 책 이름을 Header에서 표시하려면 어떻게 해야 할지 고민해보기
  const isBookDetail = pathname.includes(ROUTE_PATH.bookDetail);
  const headerTitle = isBookDetail
    ? PAGE_TITLE["book-detail"].korean
    : PAGE_TITLE[pathname].korean;

  return (
    <AppBar position="fixed" open={isSideOpen} elevation={1}>
      <Toolbar
        sx={{
          backgroundColor: (theme) => theme.palette.background.paper,
          color: (theme) => theme.palette.text.primary,
        }}>
        <IconButton
          onClick={toggleSidebar}
          sx={{
            ...(isSideOpen && { display: "none" }),
          }}>
          <LogoIcon />
        </IconButton>
        <Typography
          variant="h4"
          noWrap
          component="div"
          sx={{ fontFamily: "SOYO Maple Bold", flexGrow: 1, paddingLeft: 1 }}>
          {headerTitle ?? ""}
        </Typography>
        <Wrapper>
          <ThemeSwitch />
          {isLogin ? <AccountPopover /> : <GoogleLoginButton />}
        </Wrapper>
      </Toolbar>
    </AppBar>
  );
}
