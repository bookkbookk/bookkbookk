import { TextField } from "@mui/material";

export default function ContentField() {
  return (
    <TextField
      placeholder="내용을 입력하세요"
      rows={5}
      multiline
      sx={{ mx: 2 }}
      variant="standard"
    />
  );
}
