import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";
import { numberRegex } from "@utils/constants";
import { validatePageNumber } from "@utils/index";
import {
  useBookmarkState,
  useNewBookmarkActions,
} from "context/BookmarkProvider/useBookmark";

export default function BookmarkPageField({
  isEditing,
}: {
  isEditing: boolean;
}) {
  const { page } = useBookmarkState();
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
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "5rem" }}
      InputProps={{
        disabled: isEditing ? false : true,
        startAdornment: (
          <InputAdornment position="start">
            <Typography variant="caption">p.</Typography>
          </InputAdornment>
        ),
      }}
      variant="standard"
      value={page}
      onChange={onChange}
      helperText={!isValid && message}
    />
  );
}
