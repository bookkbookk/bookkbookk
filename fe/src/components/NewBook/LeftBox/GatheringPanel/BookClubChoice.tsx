import { RoundButton } from "@components/common/common.style";
import { Box, Typography } from "@mui/material";
import { useState } from "react";
import BookClubModal from "../Modals/BookClubModal";

export function BookClubChoice() {
  const [isOpen, setIsOpen] = useState(false);
  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: "1rem" }}>
      <Typography variant="h3">북클럽 선택하기</Typography>
      <RoundButton variant="outlined" onClick={handleOpen}>
        북클럽 선택
      </RoundButton>
      <BookClubModal open={isOpen} handleClose={handleClose} />
    </Box>
  );
}
