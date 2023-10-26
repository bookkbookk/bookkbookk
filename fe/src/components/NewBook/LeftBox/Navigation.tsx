import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { Box, IconButton } from "@mui/material";

export default function Navigation({
  onPrev,
  onNext,
}: {
  onPrev?: () => void;
  onNext?: () => void;
}) {
  return (
    <Box
      sx={{
        display: "flex",
        width: "100%",
        justifyContent: "space-between",
        position: "absolute",
        top: 50,
        left: 0,
      }}>
      {onPrev && (
        <IconButton size="small" onClick={onPrev}>
          <ArrowBackIosNewIcon />
        </IconButton>
      )}
      {onNext && (
        <IconButton size="small" onClick={onNext}>
          <ArrowForwardIosIcon />
        </IconButton>
      )}
    </Box>
  );
}
