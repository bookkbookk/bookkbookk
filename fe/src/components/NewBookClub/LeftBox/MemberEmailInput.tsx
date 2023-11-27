import useInput from "@hooks/useInput";
import { Button, Stack, TextField } from "@mui/material";
import { validateEmail } from "@utils/index";

export default function MemberEmailInput({
  addMemberEmail,
}: {
  addMemberEmail: (email: string) => void;
}) {
  const { value, onChange, error } = useInput({
    validators: [validateEmail],
  });

  const onClickAddMemberEmail = () => {
    addMemberEmail(value);
    onChange("");
  };

  return (
    <Stack
      display="flex"
      flexDirection="row"
      gap={1}
      width="100%"
      minHeight={80}>
      <TextField
        sx={{ flexGrow: 1, height: "100%" }}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        variant="outlined"
        placeholder="이메일을 입력하세요"
        helperText={!!value && error}
        error={!!error && !!value}
      />
      <Button
        variant="contained"
        onClick={onClickAddMemberEmail}
        disabled={!!error || !value}
        sx={{ height: 56 }}>
        추가하기
      </Button>
    </Stack>
  );
}
