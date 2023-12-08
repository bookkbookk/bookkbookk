import { Reaction } from "@api/comments/type";
import { REACTION_LIST } from "@components/constants";
import AddReactionIcon from "@mui/icons-material/AddReaction";
import { IconButton, Menu, MenuItem, Stack, Tooltip } from "@mui/material";
import React, { useState } from "react";
import { useMemberValue } from "store/useMember";

export default function ReactionMenu({
  reactions,
  onReactionClick,
}: {
  reactions: Partial<Reaction>;
  onReactionClick: (reaction: keyof Reaction) => void;
}) {
  const member = useMemberValue();

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const isOpen = !!anchorEl;

  const onIconClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const onReactionMenuClose = () => {
    setAnchorEl(null);
  };

  const getIsReacted = (reaction: keyof Reaction) => {
    return reactions[reaction]?.includes(member?.nickname ?? "");
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
          onClick={onIconClick}>
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
        onClose={onReactionMenuClose}>
        {REACTION_LIST.map(([name, info]) => (
          <MenuItem
            key={info.id}
            onClick={() => onReactionClick(name)}
            selected={getIsReacted(name)}>
            {String.fromCodePoint(parseInt(info.unicode, 16))}
          </MenuItem>
        ))}
      </Menu>
    </Stack>
  );
}
