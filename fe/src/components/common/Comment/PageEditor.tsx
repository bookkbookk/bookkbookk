import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";
import { numberRegex } from "@utils/constants";
import { validatePageNumber } from "@utils/index";
import { useState } from "react";

export default function PageEditor({
  value,
  onChange,
}: {
  value: string;
  onChange: (value: string) => void;
}) {
  const [pageValue, setPageValue] = useState(value);
  const { isValid, message } = validatePageNumber(pageValue);

  const onValueChange = (value: string) => {
    if (value === "") {
      setPageValue(value);
      onChange("");
      return;
    }

    if (numberRegex.test(value)) {
      onChange(value);
      setPageValue(value);
    }
  };

  return (
    <TextField
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "8rem" }}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <Typography variant="caption">p.</Typography>
          </InputAdornment>
        ),
      }}
      variant="standard"
      value={pageValue}
      onChange={(e) => onValueChange(e.target.value.trim())}
      helperText={!isValid && message}
    />
  );
}
