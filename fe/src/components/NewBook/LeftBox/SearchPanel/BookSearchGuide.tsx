import BookSearchImage from "@assets/images/book-search.png";
import { Box, Typography } from "@mui/material";

export function BookSearchGuide() {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        minHeight: "500px",
      }}>
      <img
        src={BookSearchImage}
        alt="책 검색 이미지"
        width={250}
        height={250}
      />
      <Typography sx={{ marginTop: "2rem" }} variant="h4">
        이번 북클럽 모임에서는 어떤 책을 읽으시나요?
      </Typography>
      <Typography variant="body1">
        검색창에 원하시는 책 이름을 검색해보세요!
      </Typography>
    </Box>
  );
}
