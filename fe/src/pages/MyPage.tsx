import { SignUpForm } from "@components/SignUp/SignUpForm";
import { Typography } from "@mui/material";
import { useMemberValue } from "store/useMember";

export default function MyPage() {
  const memberInfo = useMemberValue();

  return (
    <div>
      <Typography variant="h3">프로필 수정</Typography>
      {memberInfo && (
        <SignUpForm
          profileImgUrl={memberInfo.profileImgUrl}
          nickname={memberInfo.nickname}
        />
      )}
    </div>
  );
}
