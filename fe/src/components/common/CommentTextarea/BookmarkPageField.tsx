import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";

export default function BookmarkPageField() {
  return (
    <TextField
      label="북마크 페이지"
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "25ch" }}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <Typography variant="caption">p.</Typography>
          </InputAdornment>
        ),
      }}
      variant="standard"
      // TODO: value, onChange, error, helperText context value 로 받아오기
      // value={value}
      // onChange={(e) => {
      //   onChange(e.target.value);
      // }}
      // helperText={!!value && error}
      // error={!!error && !!value}
    />
  );
}
