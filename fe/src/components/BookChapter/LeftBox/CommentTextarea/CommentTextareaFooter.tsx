import { VisuallyHiddenInput } from "@components/SignUp/SignUp.style";
import AttachmentIcon from "@mui/icons-material/Attachment";
import BookmarkAddIcon from "@mui/icons-material/BookmarkAdd";
import FormatBoldIcon from "@mui/icons-material/FormatBold";
import FormatItalicIcon from "@mui/icons-material/FormatItalic";
import FormatUnderlinedIcon from "@mui/icons-material/FormatUnderlined";
import {
  Button,
  IconButton,
  ToggleButtonGroup as MuiToggleButtonGroup,
  Stack,
  ToggleButton,
  styled,
} from "@mui/material";
import { useState } from "react";

export default function CommentTextareaFooter() {
  const [formats, setFormats] = useState<string[]>([]);

  const handleFormat = (
    _: React.MouseEvent<HTMLElement>,
    newFormats: string[]
  ) => {
    setFormats(newFormats);
  };

  return (
    <Stack
      display="flex"
      flexDirection="row"
      justifyContent="space-between"
      alignItems={"center"}
      paddingTop={1}
      paddingX={2}>
      <Stack display="flex" flexDirection="row" alignItems={"center"}>
        <ToggleButtonGroup
          size="small"
          value={formats}
          onChange={handleFormat}
          aria-label="text formatting">
          <ToggleButton value="bold" aria-label="bold">
            <FormatBoldIcon />
          </ToggleButton>
          <ToggleButton value="italic" aria-label="italic">
            <FormatItalicIcon />
          </ToggleButton>
          <ToggleButton value="underlined" aria-label="underlined">
            <FormatUnderlinedIcon />
          </ToggleButton>
        </ToggleButtonGroup>
        <IconButton
          color="inherit"
          sx={{ borderRadius: 1, width: 40, height: 40 }}>
          <AttachmentIcon />
          <VisuallyHiddenInput type="file" />
        </IconButton>
      </Stack>
      <Button variant="contained" startIcon={<BookmarkAddIcon />} size="small">
        작성 완료
      </Button>
    </Stack>
  );
}

const ToggleButtonGroup = styled(MuiToggleButtonGroup)(({ theme }) => ({
  "& .MuiToggleButtonGroup-grouped": {
    "border": 0,
    "&.Mui-disabled": {
      border: 0,
    },
    "&:not(:first-of-type)": {
      borderRadius: theme.shape.borderRadius,
    },
    "&:first-of-type": {
      borderRadius: theme.shape.borderRadius,
    },
  },
}));
