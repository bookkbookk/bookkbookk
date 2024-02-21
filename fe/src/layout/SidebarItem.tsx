import { ListItemText } from "@mui/material";
import ListItem from "@mui/material/ListItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import { Link, useLocation } from "react-router-dom";
import * as S from "./Layout.style";
import { NavigationItem } from "./constants";

export function SidebarItem({ item }: { item: NavigationItem }) {
  const { path, label, icon } = item;
  const { pathname } = useLocation();
  const isSelected = path.some(
    (item) => item === pathname || (pathname.includes(item) && item !== "/main")
  );

  return (
    <ListItem disablePadding>
      <S.ListItemButton component={Link} to={path[0]} selected={isSelected}>
        <ListItemIcon>{icon}</ListItemIcon>
        <ListItemText primary={label} />
      </S.ListItemButton>
    </ListItem>
  );
}
