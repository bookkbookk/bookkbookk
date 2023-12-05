import { TextField, Typography } from "@mui/material";
import InputAdornment from "@mui/material/InputAdornment";
import { numberRegex } from "@utils/constants";
import { validatePageNumber } from "@utils/index";
import { useState } from "react";

type BookmarkPageViewerProps = {
  value: string;
  disabled: true;
};

type BookmarkPageEditorProps = {
  value: string;
  onChange: (value: string) => void;
};

export function BookmarkPageEditor(props: BookmarkPageEditorProps) {
  const [value, setValue] = useState(props.value);
  const { isValid, message } = validatePageNumber(value);

  const onValueChange = (value: string) => {
    if (value === "") {
      setValue(value);
      props.onChange("");
      return;
    }

    if (numberRegex.test(value)) {
      props.onChange(value);
      setValue(value);
    }
  };

  return (
    <TextField
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "5rem" }}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <Typography variant="caption">p.</Typography>
          </InputAdornment>
        ),
      }}
      variant="standard"
      value={value}
      onChange={(e) => onValueChange(e.target.value.trim())}
      helperText={!isValid && message}
    />
  );
}

export function BookmarkPageViewer(props: BookmarkPageViewerProps) {
  const { value, disabled } = props;

  return (
    <TextField
      id="standard-start-adornment"
      sx={{ mx: 2, my: 1, width: "5rem" }}
      InputProps={{
        disabled,
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
