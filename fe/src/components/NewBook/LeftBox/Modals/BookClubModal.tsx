import { useGetBookClubList } from "@api/bookClub/queries.tsx";
import {
  ModalBody,
  ModalBox,
  ModalHeader,
} from "@components/common/common.style";
import ClearIcon from "@mui/icons-material/Clear";
import { IconButton, List, Modal, Typography } from "@mui/material";
import { useBookClubChoiceValue } from "store/newBook/useBookClubChoice";
import BookClubListItem from "./BookClubListItem";

export default function BookClubModal({
  open,
  handleClose,
}: {
  open: boolean;
  handleClose: () => void;
}) {
  const bookClubChoice = useBookClubChoiceValue();
  const { data } = useGetBookClubList();

  return (
    <Modal open={open} onClose={handleClose}>
      <ModalBox>
        <ModalHeader>
          <Typography variant="h4">북클럽 목록</Typography>
          <IconButton size="small" onClick={handleClose}>
            <ClearIcon fontSize="inherit" />
          </IconButton>
        </ModalHeader>
        <ModalBody>
          <List sx={{ width: "100%", padding: 0 }}>
            {data?.map((bookClub) => (
              <BookClubListItem
                key={bookClub.id}
                bookClub={bookClub}
                active={bookClubChoice?.id === bookClub.id}
                onSelectItem={handleClose}
              />
            ))}
          </List>
        </ModalBody>
      </ModalBox>
    </Modal>
  );
}
