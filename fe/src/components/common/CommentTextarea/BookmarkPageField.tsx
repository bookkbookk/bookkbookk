import useInput from "@hooks/useInput";
import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";
import { validatePageNumber } from "@utils/index";
import { useNewBookmarkActions } from "context/NewBookmark/useNewBookmark";

export default function BookmarkPageField() {
  const { setPage } = useNewBookmarkActions();

  const { value, onChange, error } = useInput({
    validators: [validatePageNumber],
    callback: setPage,
  });

  return (
    <TextField
      label="(선택) 북마크 페이지"
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
      value={value}
      onChange={(e) => onChange(e.target.value.trim())}
      helperText={error}
    />
  );
}
