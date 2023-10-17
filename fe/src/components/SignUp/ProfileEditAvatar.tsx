import { PlusBadge } from "@components/common/common.style";
import AddIcon from "@mui/icons-material/Add";
import { Button } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";

export default function ProfileEditAvatar({
  profileImgUrl,
  onClick,
}: {
  profileImgUrl: string;
  onClick?: () => void;
}) {
  return (
    <Button
      style={{ backgroundColor: "transparent" }}
      onClick={onClick}
      disableElevation
      disableRipple>
      <Stack direction="row" spacing={2}>
        <PlusBadge
          anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
          color="primary"
          badgeContent={<AddIcon />}>
          <Avatar sx={{ width: 150, height: 150 }} src={profileImgUrl} />
        </PlusBadge>
      </Stack>
    </Button>
  );
}
