import { useGetMember } from "@api/member/queries";
import ProfileEditAvatar from "@components/SignUp/ProfileEditAvatar";
import Title from "@components/SignUp/Title";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { Container, Wrapper } from "@components/common/common.style";
import { MESSAGE } from "@constant/index";
import TextField from "@mui/material/TextField";

export default function SignUp() {
  const { data: memberInfo, isSuccess, isError } = useGetMember();

  return (
    <Container>
      <Title />
      {isSuccess && <SignUpForm {...memberInfo} />}
      {isError && (
        <StatusIndicator status="error" message={MESSAGE.MEMBER_ERROR} />
      )}
    </Container>
  );
}

function SignUpForm({
  profileImgUrl,
  nickname,
}: {
  profileImgUrl: string;
  nickname: string;
}) {
  return (
    <Wrapper>
      <ProfileEditAvatar {...{ profileImgUrl }} />
      <TextField
        id="nickname"
        label="닉네임"
        variant="outlined"
        defaultValue={nickname}
        // error
        // helperText="이미 사용중인 닉네임입니다."
      />
    </Wrapper>
  );
}
