import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import { IconButton } from "@mui/material";
import Divider from "@mui/material/Divider";
import List from "@mui/material/List";
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
          <SidebarItem key={item.title} {...{ isOpen, ...item }} />
        ))}
      </List>
    </S.Sidebar>
  );
}
