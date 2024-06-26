import { usePatchBookStatus } from "@api/book/queries";
import { BookChapterStatusID } from "@api/book/type";
import { BOOK_CHAPTERS_STATUS_LIST } from "@components/constants";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { IconButton, Menu, MenuItem, Stack, Tooltip } from "@mui/material";
import React, { useState } from "react";

export default function BookStatusMenu({
  bookId,
  statusId,
  onChangeBookStatus,
}: {
  bookId: number;
  statusId: BookChapterStatusID;
  onChangeBookStatus: (statusId: BookChapterStatusID) => void;
}) {
  const { onPatchBookStatus } = usePatchBookStatus({
    onSuccessCallback: onChangeBookStatus,
  });
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const isOpen = !!anchorEl;

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const onMenuClick = (statusId: BookChapterStatusID) => {
    onPatchBookStatus({ bookId, statusId });
    handleClose();
  };

  return (
    <Stack>
      <Tooltip title="북클럽의 독서 상태를 변경하세요" placement="top" arrow>
        <IconButton
          aria-label="more"
          id="status-menu-button"
          aria-controls={isOpen ? "status-menu" : undefined}
          aria-expanded={isOpen ? "true" : undefined}
          aria-haspopup="true"
          size="small"
          onClick={handleClick}>
          <MoreVertIcon />
        </IconButton>
      </Tooltip>
      <Menu
        id="status-menu"
        MenuListProps={{
          "aria-labelledby": "status-menu-button",
        }}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "right",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        anchorEl={anchorEl}
        open={isOpen}
        onClose={handleClose}>
        {BOOK_CHAPTERS_STATUS_LIST.filter((s) => s.id !== statusId).map(
          (option) => (
            <MenuItem key={option.id} onClick={() => onMenuClick(option.id)}>
              {`${option.label} 상태로 변경`}
            </MenuItem>
          )
        )}
      </Menu>
    </Stack>
  );
}
