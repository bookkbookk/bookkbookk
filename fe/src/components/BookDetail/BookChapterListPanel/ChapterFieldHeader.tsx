import { Stack, Typography, styled } from "@mui/material";

export function ChapterFieldHeader() {
  return (
    <Stack
      display="flex"
      flexDirection={"row"}
      paddingBottom={2}
      paddingTop={4}
      marginX={"5%"}
      width="90%">
      <FieldTitle variant="body2" sx={{ width: "10%" }}>
        진행 상태
      </FieldTitle>
      <FieldTitle variant="body2" sx={{ width: "40%" }}>
        챕터 제목
      </FieldTitle>
      <FieldTitle variant="body2" sx={{ width: "50%" }}>
        최신 북마크
      </FieldTitle>
    </Stack>
  );
}

const FieldTitle = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  textAlign: "center",
}));
