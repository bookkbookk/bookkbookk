import { GoogleLoginButton } from "@components/common/GoogleLoginButton";
import Logo from "@components/common/Logo";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { PAGE_TITLE } from "@constant/index";
import { IconButton, Toolbar, Typography } from "@mui/material";
import { useIsLoginValue } from "store/useMember";
import AccountPopover from "./AccountPopover.tsx/AccountPopover";
import { AppBar } from "./Layout.style";

export function Header({
  isSideOpen,
  currentPagePath,
  toggleSidebar,
}: {
  isSideOpen: boolean;
  currentPagePath: string;
  toggleSidebar: () => void;
}) {
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
          <Logo isTitleVisible={false} />
        </IconButton>
        <Typography
          variant="h4"
          noWrap
          component="div"
          sx={{ fontFamily: "SOYO Maple Bold", flexGrow: 1 }}>
          {PAGE_TITLE[currentPagePath]}
        </Typography>
        <ThemeSwitch />
        {isLogin ? <AccountPopover /> : <GoogleLoginButton />}
      </Toolbar>
    </AppBar>
  );
}
