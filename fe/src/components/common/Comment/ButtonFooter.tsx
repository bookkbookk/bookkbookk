import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import { Button, Stack } from "@mui/material";

export default function ButtonFooter({
  onCancelClick,
  onPostClick,
}: {
  onCancelClick: () => void;
  onPostClick: () => void;
}) {
  return (
    <Stack width="100%" padding={2}>
      <Stack display="flex" flexDirection="row" gap={1} alignSelf="end">
        <Button
          variant="outlined"
          startIcon={<ClearIcon />}
          size="small"
          onClick={onCancelClick}
          sx={{ alignSelf: "flex-end" }}>
          작성 취소
        </Button>
        <Button
          variant="contained"
          startIcon={<CheckIcon />}
          size="small"
          onClick={onPostClick}
          sx={{ alignSelf: "flex-end" }}>
          작성 완료
        </Button>
      </Stack>
    </Stack>
  );
}
