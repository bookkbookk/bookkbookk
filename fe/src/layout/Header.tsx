import { ReactComponent as LogoIcon } from "@assets/images/brand.svg";
import AccountPopover from "@components/common/AccountPopover/AccountPopover";
import { GoogleLoginButton } from "@components/common/GoogleLoginButton";
import ThemeSwitch from "@components/common/ThemeSwitch/ThemeSwitch";
import { Wrapper } from "@components/common/common.style";
import { IconButton, Toolbar } from "@mui/material";
import { useIsLoginValue } from "store/useMember";
import HeaderTitle from "./HeaderTitle";
import { AppBar } from "./Layout.style";

export function Header({
  isSideOpen,
  toggleSidebar,
}: {
  isSideOpen: boolean;
  toggleSidebar: () => void;
}) {
  const isLogin = useIsLoginValue();

  return (
    <AppBar position="fixed" open={isSideOpen} elevation={1}>
      <Toolbar
        sx={{
          justifyContent: "space-between",
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
        <HeaderTitle />
        <Wrapper>
          <ThemeSwitch />
          {isLogin ? <AccountPopover /> : <GoogleLoginButton />}
        </Wrapper>
      </Toolbar>
    </AppBar>
  );
}
