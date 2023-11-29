import { REACTION_LIST } from "@components/constants";
import AddReactionIcon from "@mui/icons-material/AddReaction";
import { IconButton, Menu, MenuItem, Stack, Tooltip } from "@mui/material";
import React, { useState } from "react";

export default function ReactionMenu() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const isOpen = !!anchorEl;

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <Stack color="inherit">
      <Tooltip title="리액션을 추가하세요!" placement="top" arrow>
        <IconButton
          aria-label="reaction-menu-button"
          id="reaction-menu-button"
          aria-controls={isOpen ? "reaction-menu" : undefined}
          aria-expanded={isOpen ? "true" : undefined}
          aria-haspopup="true"
          color="inherit"
          onClick={handleClick}>
          <AddReactionIcon fontSize="small" />
        </IconButton>
      </Tooltip>
      <Menu
        id="reaction-menu"
        MenuListProps={{
          "aria-labelledby": "reaction-menu-button",
        }}
        anchorOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
        transformOrigin={{
          vertical: "bottom",
          horizontal: "right",
        }}
        anchorEl={anchorEl}
        open={isOpen}
        onClose={handleClose}>
        {REACTION_LIST.map((option) => (
          <MenuItem key={option.id}>
            {String.fromCodePoint(parseInt(option.unicode, 16))}
          </MenuItem>
        ))}
      </Menu>
    </Stack>
  );
}
