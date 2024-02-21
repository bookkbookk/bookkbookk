import KeyboardArrowLeftIcon from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRightIcon from "@mui/icons-material/KeyboardArrowRight";
import { Box, Button } from "@mui/material";

export default function Navigation({
  onPrev,
  onNext,
}: {
  onPrev?: {
    onClick: () => void;
    text?: string;
  };
  onNext?: {
    onClick: () => void;
    text?: string;
  };
}) {
  return (
    <Box
      sx={{
        display: "flex",
        width: "100%",
        justifyContent: "space-between",
        position: "absolute",
        top: "4%",
      }}>
      <Box>
        {onPrev && (
          <Button onClick={onPrev.onClick}>
            <KeyboardArrowLeftIcon />
            {onPrev.text}
          </Button>
        )}
      </Box>
      <Box>
        {onNext && (
          <Button onClick={onNext.onClick}>
            {onNext.text}
            <KeyboardArrowRightIcon />
          </Button>
        )}
      </Box>
    </Box>
  );
}
