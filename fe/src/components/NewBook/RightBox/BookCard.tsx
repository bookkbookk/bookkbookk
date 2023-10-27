import * as S from "@components/NewBook/style";
import { Card, CardContent } from "@components/common/common.style";
import ClearIcon from "@mui/icons-material/Clear";
import LaunchIcon from "@mui/icons-material/Launch";
import { Box, IconButton, Tooltip, Typography } from "@mui/material";
import { useBookChoice } from "store/newBook/useBookChoice";

export default function BookCard() {
  const [bookChoice, setBookChoice] = useBookChoice();

  return (
    <Card>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h6">책 정보</Typography>
        {bookChoice && (
          <div>
            <Tooltip
              title="서점 페이지의 도서 정보로 이동해요!"
              placement="top-start"
              arrow>
              <IconButton size="small" href={bookChoice.link} target="blank">
                <LaunchIcon fontSize="inherit" />
              </IconButton>
            </Tooltip>
            <Tooltip title="취소" placement="top-end" arrow>
              <IconButton
                size="small"
                onClick={() => {
                  setBookChoice(null);
                }}>
                <ClearIcon fontSize="inherit" />
              </IconButton>
            </Tooltip>
          </div>
        )}
      </Box>
      {bookChoice && (
        <CardContent>
          <S.BookCoverImage
            src={bookChoice.cover}
            alt={bookChoice.title}
            width={80}
            height={100}
          />
          <Box sx={{ display: "flex", flexDirection: "column", width: "100%" }}>
            <Typography variant="body1">{bookChoice.title}</Typography>
            <S.BookDescription variant="body2">
              {bookChoice.author}
            </S.BookDescription>
          </Box>
        </CardContent>
      )}
    </Card>
  );
}
