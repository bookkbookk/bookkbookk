import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";

export default function BookmarkPageField({
  isEditing,
}: {
  isEditing: boolean;
}) {
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
    />
  );
}
