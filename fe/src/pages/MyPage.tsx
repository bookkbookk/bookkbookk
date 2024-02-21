import { SignUpForm } from "@components/SignUp/SignUpForm";
import { Box } from "@mui/material";
import { useMemberValue } from "store/useMember";

export default function MyPage() {
  const memberInfo = useMemberValue();

  // TODO: 마이페이지 기능별로 컴포넌트 분리
  return (
    <Box
      sx={{
        width: "100%",
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}>
      <div>
        {memberInfo && (
          <SignUpForm
            profileImgUrl={memberInfo.profileImgUrl}
            nickname={memberInfo.nickname}
          />
        )}
      </div>
    </Box>
  );
}
