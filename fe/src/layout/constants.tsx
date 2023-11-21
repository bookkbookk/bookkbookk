import { PAGE_TITLE } from "@constant/index";
import Diversity3RoundedIcon from "@mui/icons-material/Diversity3Rounded";
import GridViewRoundedIcon from "@mui/icons-material/GridViewRounded";
import MenuBookRoundedIcon from "@mui/icons-material/MenuBookRounded";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";
import { ROUTE_PATH } from "routes/constants";

export const SIDEBAR = {
  openWidth: 240,
  closeWidth: 56,
};

export const HEADER = {
  height: 64,
};

export type NavigationItem = {
  label: string;
  path: string[];
  icon: React.ReactElement;
};

export const NAVIGATION_CONFIG = [
  {
    label: PAGE_TITLE[ROUTE_PATH.main].english,
    path: [ROUTE_PATH.main],
    icon: <GridViewRoundedIcon />,
  },
  {
    label: PAGE_TITLE[ROUTE_PATH.library].english,
    path: [
      ROUTE_PATH.library,
      ROUTE_PATH.newBook,
      ROUTE_PATH.bookDetail,
      ROUTE_PATH.chapter,
    ],
    icon: <MenuBookRoundedIcon />,
  },
  {
    label: PAGE_TITLE[ROUTE_PATH.bookClub].english,
    path: [ROUTE_PATH.bookClub, ROUTE_PATH.newBookClub],
    icon: <Diversity3RoundedIcon />,
  },
  {
    label: PAGE_TITLE[ROUTE_PATH.myPage].english,
    path: [ROUTE_PATH.myPage],
    icon: <SettingsRoundedIcon />,
  },
];
