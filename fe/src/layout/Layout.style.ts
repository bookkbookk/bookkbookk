import { Box } from "@mui/material";
import MuiAppBar, { AppBarProps as MuiAppBarProps } from "@mui/material/AppBar";
import Drawer from "@mui/material/Drawer";
import MuiListItemButton from "@mui/material/ListItemButton";
import { CSSObject, Theme, alpha, styled } from "@mui/material/styles";
import { SIDEBAR } from "./constants";

type AppBarProps = MuiAppBarProps & {
  open?: boolean;
};

const openedMixin = (theme: Theme): CSSObject => ({
  width: SIDEBAR.width,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: "hidden",
});

const closedMixin = (theme: Theme): CSSObject => ({
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: "hidden",
  width: theme.spacing(7),
  [theme.breakpoints.up("sm")]: {
    width: theme.spacing(8),
  },
});

export const Sidebar = styled(Drawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  width: SIDEBAR.width,
  flexShrink: 0,
  whiteSpace: "nowrap",
  boxSizing: "border-box",
  ...(open && {
    ...openedMixin(theme),
    "& .MuiDrawer-paper": openedMixin(theme),
  }),
  ...(!open && {
    ...closedMixin(theme),
    "& .MuiDrawer-paper": closedMixin(theme),
  }),
}));

export const SidebarHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  ...theme.mixins.toolbar,
}));

export const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})<AppBarProps>(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(["width", "margin"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    width: `calc(100% - ${SIDEBAR.width}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

type ListItemButtonProps = React.ComponentPropsWithRef<
  typeof MuiListItemButton
> & {
  component: React.ElementType;
  to: string;
  selected?: boolean;
};

// TODO: 매직넘버 제거
export const ListItemButton = styled(MuiListItemButton, {
  shouldForwardProp: (prop) => prop !== "selected",
})<ListItemButtonProps>(({ theme, selected }) => ({
  minHeight: 48,
  color: theme.palette.text.secondary,
  textTransform: "capitalize",
  ...(selected && {
    "color": theme.palette.primary.main,
    "backgroundColor": alpha(theme.palette.primary.main, 0.08),
    "&:hover": {
      backgroundColor: alpha(theme.palette.primary.main, 0.16),
    },
    "& .MuiListItemIcon-root": {
      color: selected
        ? theme.palette.primary.main
        : theme.palette.text.secondary,
    },
  }),
}));

export const Main = styled(Box)`
  width: 100vw;
  height: 100vh;
  display: flex;
`;

export const Content = styled(Box)`
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
`;
