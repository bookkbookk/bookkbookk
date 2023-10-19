import { Box } from "@mui/material";
import { useState } from "react";
import { Header } from "./Header";
import { Main, SidebarHeader } from "./Layout.style";
import Sidebar from "./Sidebar";

export default function Layout({ children }: { children: React.ReactNode }) {
  const [isSideOpen, setIsSideOpen] = useState(false);

  const toggleSidebar = () => setIsSideOpen(!isSideOpen);

  return (
    <Box>
      <Header {...{ isSideOpen, toggleSidebar }} />
      <Sidebar isOpen={isSideOpen} toggleSidebar={toggleSidebar} />
      <Main>
        <SidebarHeader />
        {children}
      </Main>
    </Box>
  );
}
