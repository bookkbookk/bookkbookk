import { useGetMember } from "@api/member/queries";
import { SignUpForm } from "@components/SignUp/SignUpForm";
import Title from "@components/SignUp/Title";
import Logo from "@components/common/Logo";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { MESSAGE } from "@constant/index";
import { Box } from "@mui/material";

export default function SignUp() {
  const { data: memberInfo, isSuccess, isError } = useGetMember();

  return (
    <Box>
      <div style={{ width: "100%", padding: "2rem 0" }}>
        <Logo isTitleVisible={false} />
      </div>
      <Title />
      {isSuccess && <SignUpForm {...memberInfo} />}
      {isError && (
        <StatusIndicator status="error" message={MESSAGE.MEMBER_ERROR} />
      )}
    </Box>
  );
}
