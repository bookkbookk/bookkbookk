import { useGetMember } from "@api/member/queries";
import { SignUpForm } from "@components/SignUp/SignUpForm";
import Title from "@components/SignUp/Title";
import Logo from "@components/common/Logo";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { Container } from "@components/common/common.style";
import { MESSAGE } from "@constant/index";

export default function SignUp() {
  const { data: memberInfo, isSuccess, isError } = useGetMember();

  return (
    <Container>
      <div style={{ width: "100%" }}>
        <Logo />
      </div>
      <Title />
      {isSuccess && <SignUpForm {...memberInfo} />}
      {isError && (
        <StatusIndicator status="error" message={MESSAGE.MEMBER_ERROR} />
      )}
    </Container>
  );
}
