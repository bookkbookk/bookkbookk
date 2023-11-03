import {
  ModalBody,
  ModalBox,
  ModalHeader,
} from "@components/common/common.style";
import ClearIcon from "@mui/icons-material/Clear";
import { IconButton, Modal, Typography } from "@mui/material";

export default function GatheringAddModal({
  open,
  handleClose,
}: {
  open: boolean;
  handleClose: () => void;
}) {
  return (
    <Modal open={open} onClose={handleClose}>
      <ModalBox>
        <ModalHeader>
          <Typography variant="h5">새로운 모임 생성</Typography>
          <IconButton size="small" onClick={handleClose}>
            <ClearIcon fontSize="inherit" />
          </IconButton>
        </ModalHeader>
        <ModalBody />
      </ModalBox>
    </Modal>
  );
}

// function GatheringBookChoice() {}

// function GatheringList() {}
