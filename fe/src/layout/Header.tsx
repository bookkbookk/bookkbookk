import { ReactComponent as LogoIcon } from "@assets/images/brand.svg";
import { GoogleLoginButton } from "@components/common/GoogleLoginButton";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Wrapper } from "@components/common/common.style";
import { PAGE_TITLE } from "@constant/index";
import { IconButton, Toolbar, Typography } from "@mui/material";
import { useLocation } from "react-router-dom";
import { useIsLoginValue } from "store/useMember";
import AccountPopover from "./AccountPopover.tsx/AccountPopover";
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

  return (
    <AppBar position="fixed" open={isSideOpen}>
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
          {PAGE_TITLE[pathname].korean}
        </Typography>
        <Wrapper>
          <ThemeSwitch />
          {isLogin ? <AccountPopover /> : <GoogleLoginButton />}
        </Wrapper>
      </Toolbar>
    </AppBar>
  );
}
