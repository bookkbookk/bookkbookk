import Diversity2RoundedIcon from "@mui/icons-material/Diversity2Rounded";
import GridViewRoundedIcon from "@mui/icons-material/GridViewRounded";
import MenuBookRoundedIcon from "@mui/icons-material/MenuBookRounded";
import { ROUTE_PATH } from "routes/constants";

export const SIDEBAR = {
  width: 240,
};

export const HEADER = {
  mobile: 64,
  desktop: 80,
};

export type NavigationItem = {
  title: string;
  path: string;
  icon: React.ReactElement;
};

export const NAVIGATION_CONFIG = [
  {
    title: "메인 main",
    path: ROUTE_PATH.main,
    icon: <GridViewRoundedIcon />,
  },
  {
    title: "서재 library",
    path: ROUTE_PATH.library,
    icon: <MenuBookRoundedIcon />,
  },
  {
    title: "북클럽 bookClub",
    path: ROUTE_PATH.bookClub,
    icon: <Diversity2RoundedIcon />,
  },
];
