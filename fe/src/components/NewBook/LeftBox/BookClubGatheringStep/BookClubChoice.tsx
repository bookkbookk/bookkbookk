import { CardContent } from "@components/common/common.style";
import { Avatar, Box, Button, Typography } from "@mui/material";
import { useState } from "react";
import { useBookClubChoiceValue } from "store/newBook/useBookClubChoice";
import BookClubModal from "../Modals/BookClubModal";

export function BookClubChoice() {
  const [isOpen, setIsOpen] = useState(false);
  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  const bookClubChoice = useBookClubChoiceValue();

  return (
    <Box
      sx={{
        display: "flex",
        width: "100%",
        flexDirection: "column",
        gap: "1rem",
      }}>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h3">
          이 책을 어떤 북클럽에서 읽으시나요?
        </Typography>
        <Button variant="contained" onClick={handleOpen}>
          북클럽 선택하기
        </Button>
      </Box>
      <CardContent sx={{ padding: "1rem" }}>
        {bookClubChoice && (
          <>
            <Avatar
              sx={{ width: "3rem", height: "3rem" }}
              src={bookClubChoice.profileImgUrl}
              alt={bookClubChoice.name}
            />
            <Typography variant="h5" color="GrayText">
              {bookClubChoice.name}
            </Typography>
          </>
        )}
      </CardContent>
      <BookClubModal open={isOpen} handleClose={handleClose} />
    </Box>
  );
}
