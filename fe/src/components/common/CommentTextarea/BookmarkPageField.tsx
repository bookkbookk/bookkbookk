import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";
import { numberRegex } from "@utils/constants";
import { validatePageNumber } from "@utils/index";
import {
  useNewBookmarkActions,
  useNewBookmarkState,
} from "context/NewBookmark/useNewBookmark";

export default function BookmarkPageField() {
  const { page } = useNewBookmarkState();
  const { setPage } = useNewBookmarkActions();
  const { isValid, message } = validatePageNumber(page ?? "");

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.trim();

    if (value === "") {
      return setPage("");
    }

    numberRegex.test(value) && setPage(value);
  };

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
      value={page ?? ""}
      onChange={onChange}
      helperText={!isValid && message}
    />
  );
}
