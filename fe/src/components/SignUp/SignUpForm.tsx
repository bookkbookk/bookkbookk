import { usePatchMemberInfo } from "@api/member/queries";
import ProfileEditAvatar from "@components/SignUp/ProfileEditAvatar";
import { Wrapper } from "@components/SignUp/SignUp.style";
import { useFileReader } from "@hooks/useFileReader";
import { useMovePage } from "@hooks/useMovePage";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import { useState } from "react";
import { ROUTE_PATH } from "routes/constants";

export function SignUpForm({
  profileImgUrl,
  nickname,
}: {
  profileImgUrl: string;
  nickname: string;
}) {
  const [newNickname, setNewNickname] = useState(nickname);
  const { file, previewUrl, handleFileChange } = useFileReader();
  const { movePage } = useMovePage({ replace: true });

  const { onPatchMemberInfo } = usePatchMemberInfo({
    onSuccessCallback: () => movePage(ROUTE_PATH.main),
  });
  const onSubmitMemberInfo = () =>
    onPatchMemberInfo({
      nickname: newNickname,
      profileImage: file,
    });

  const isSameProfile = newNickname === nickname && !file;
  const previewImgUrl = previewUrl ?? profileImgUrl;

  const onNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setNewNickname(e.target.value);

  const onCompleteButtonClick = isSameProfile
    ? () => movePage(ROUTE_PATH.main)
    : onSubmitMemberInfo;

  return (
    <Wrapper>
      <ProfileEditAvatar
        profileImgUrl={previewImgUrl}
        onFileChange={handleFileChange}
      />
      <TextField
        id="nickname"
        label="닉네임"
        variant="outlined"
        sx={{ width: 300 }}
        value={newNickname}
        onChange={onNicknameChange}
      />
      <Button
        variant="contained"
        color="primary"
        className="submit-button"
        onClick={onCompleteButtonClick}
        disabled={!newNickname}>
        완료
      </Button>
    </Wrapper>
  );
}
