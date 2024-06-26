import { PlusBadge } from "@components/common/common.style";
import AddIcon from "@mui/icons-material/Add";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import * as S from "./SignUp.style";

export default function ProfileEditAvatar({
  profileImgUrl,
  onFileChange,
}: {
  profileImgUrl: string;
  onFileChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}) {
  return (
    <Button sx={{ backgroundColor: "transparent" }} component="label">
      <S.VisuallyHiddenInput
        accept="image/*"
        id="profileImg"
        type="file"
        onChange={onFileChange}
      />
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
