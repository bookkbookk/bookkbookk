import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";

export default function PageViewer({ value }: { value: string }) {
  return (
    <TextField
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "3rem" }}
      InputProps={{
        disabled: true,
        startAdornment: (
          <InputAdornment position="start">
            <Typography variant="caption">p.</Typography>
          </InputAdornment>
        ),
      }}
      variant="standard"
      value={value}
    />
  );
}
