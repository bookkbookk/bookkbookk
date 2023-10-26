import { useLogout } from "@api/auth/queries";
import { Divider, MenuItem, Popover as MuiPopover } from "@mui/material";
import AccountInfo from "./AccountInfo";

export default function Popover({
  openTrigger,
  handleClose,
}: {
  openTrigger: HTMLElement;
  handleClose: () => void;
}) {
  const { onLogout } = useLogout();
  const onLogoutClick = () => {
    onLogout();
    handleClose();
  };

  return (
    <MuiPopover
      open={!!openTrigger}
      anchorEl={openTrigger}
      onClose={handleClose}
      anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
      transformOrigin={{ vertical: "top", horizontal: "right" }}>
      <AccountInfo />
      <Divider sx={{ borderStyle: "dashed" }} />
      <MenuItem
        disableRipple
        disableTouchRipple
        onClick={onLogoutClick}
        sx={{ color: "error.main", py: 1.5 }}>
        로그아웃
      </MenuItem>
    </MuiPopover>
  );
}
