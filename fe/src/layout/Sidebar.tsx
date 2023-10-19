import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import { IconButton } from "@mui/material";
import Divider from "@mui/material/Divider";
import List from "@mui/material/List";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import * as S from "./Layout.style";
import { SidebarHeader } from "./Layout.style";
import { SidebarItem } from "./SidebarItem";
import { NAVIGATION_CONFIG } from "./constants";

export default function Sidebar({
  isOpen,
  toggleSidebar,
}: {
  isOpen: boolean;
  toggleSidebar: () => void;
}) {
  const { pathname } = useLocation();

  useEffect(() => {
    if (isOpen) {
      toggleSidebar();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pathname]);

  return (
    <S.Sidebar variant="permanent" open={isOpen}>
      <SidebarHeader>
        <IconButton onClick={toggleSidebar}>
          <ChevronLeftIcon />
        </IconButton>
      </SidebarHeader>
      <Divider />
      <List sx={{ padding: 0 }}>
        {NAVIGATION_CONFIG.map((item) => (
          <SidebarItem key={item.label} item={item} />
        ))}
      </List>
    </S.Sidebar>
  );
}
