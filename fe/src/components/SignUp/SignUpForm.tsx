import { usePatchMemberInfo } from "@api/member/queries";
import ProfileEditAvatar from "@components/SignUp/ProfileEditAvatar";
import { Wrapper } from "@components/SignUp/SignUp.style";
import { useFileReader } from "@hooks/useFileReader";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useSetMember } from "store/useMember";

export function SignUpForm({
  profileImgUrl,
  nickname,
}: {
  profileImgUrl: string;
  nickname: string;
}) {
  const setMemberInfo = useSetMember();
  const [newNickname, setNewNickname] = useState(nickname);
  const { file, previewUrl, handleFileChange } = useFileReader();
  const navigate = useNavigate();

  const goMainPage = () => navigate(ROUTE_PATH.main, { replace: true });

  const { onPatchMemberInfo } = usePatchMemberInfo({
    onSuccessCallback: (newMemberInfo) => {
      setMemberInfo({
        type: "UPDATE",
        payload: {
          nickname: newMemberInfo.newNickname,
          profileImgUrl: newMemberInfo.newProfileImgUrl,
        },
      });
      goMainPage();
    },
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

  const onCompleteButtonClick = isSameProfile ? goMainPage : onSubmitMemberInfo;

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
