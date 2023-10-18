import { Box } from "@mui/material";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { Header } from "./Header";
import { Main } from "./Layout.style";
import Sidebar from "./Sidebar";

export default function Layout({ children }: { children: React.ReactNode }) {
  const location = useLocation();
  const [isSideOpen, setIsSideOpen] = useState(false);

  const currentPagePath = location.pathname;
  const toggleSidebar = () => setIsSideOpen(!isSideOpen);

  return (
    <Box>
      <Header {...{ isSideOpen, currentPagePath, toggleSidebar }} />
      <Sidebar isOpen={isSideOpen} toggleSidebar={toggleSidebar} />
      <Main>{children}</Main>
    </Box>
  );
}
