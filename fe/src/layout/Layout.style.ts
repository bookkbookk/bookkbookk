import { Box } from "@mui/material";
import MuiAppBar, { AppBarProps as MuiAppBarProps } from "@mui/material/AppBar";
import Drawer from "@mui/material/Drawer";
import MuiListItemButton from "@mui/material/ListItemButton";
import { CSSObject, Theme, alpha, styled } from "@mui/material/styles";
import { HEADER, SIDEBAR } from "./constants";

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
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up("sm")]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
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
  padding: theme.spacing(0, 1),
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

export const Main = styled(Box)`
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  py: ${HEADER.mobile + 8}px;
`;

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
  paddingLeft: theme.spacing(2.5),
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
