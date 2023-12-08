import { BookChapterStatusID } from "@api/book/type";
import { usePatchChapter } from "@api/chapters/queries";
import StatusChip from "@components/common/StatusChip";
import { BOOK_CHAPTERS_STATUS_LIST } from "@components/constants";
import { Menu, MenuItem } from "@mui/material";
import React, { useState } from "react";

export default function ChapterStatusMenu({
  chapterId,
  statusId,
}: {
  chapterId: number;
  statusId: BookChapterStatusID;
}) {
  const [chapterStatus, setChapterStatus] = useState(statusId);
  const onStatusChange = (statusId: BookChapterStatusID) => {
    setChapterStatus(statusId);
  };

  const { onPatchChapterStatus } = usePatchChapter({
    onStatusChange,
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
    onPatchChapterStatus({ chapterId, statusId });
    handleClose();
  };

  return (
    <>
      <StatusChip statusId={chapterStatus} onClick={handleClick} />
      <Menu
        id="chapter-status-menu"
        MenuListProps={{
          "aria-labelledby": "chapter-status-menu-button",
        }}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        anchorEl={anchorEl}
        open={isOpen}
        onClose={handleClose}>
        {BOOK_CHAPTERS_STATUS_LIST.filter((s) => s.id !== chapterStatus).map(
          (option) => (
            <MenuItem key={option.id} onClick={() => onMenuClick(option.id)}>
              {`${option.label} 상태로 변경`}
            </MenuItem>
          )
        )}
      </Menu>
    </>
  );
}
