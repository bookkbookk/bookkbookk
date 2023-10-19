import { Avatar, IconButton, alpha } from "@mui/material";
import { useMemberValue } from "store/useMember";

export default function AvatarButton({
  isOpen,
  handleOpen,
}: {
  isOpen: boolean;
  handleOpen: (event: React.MouseEvent<HTMLButtonElement>) => void;
}) {
  const member = useMemberValue();

  return (
    <IconButton
      onClick={handleOpen}
      sx={{
        width: 40,
        height: 40,
        background: (theme) => alpha(theme.palette.grey[500], 0.08),
        ...(isOpen && {
          background: (theme) =>
            `linear-gradient(135deg, ${theme.palette.primary.light} 0%, ${theme.palette.primary.main} 100%)`,
        }),
      }}>
      {member && (
        <Avatar
          src={member.profileImgUrl}
          alt={member.nickname}
          sx={{
            width: 36,
            height: 36,
            border: (theme) => `solid 2px ${theme.palette.background.default}`,
          }}
        />
      )}
    </IconButton>
  );
}
