import * as S from "@components/NewBook/style";
import { Card, CardContent } from "@components/common/common.style";
import ClearIcon from "@mui/icons-material/Clear";
import LaunchIcon from "@mui/icons-material/Launch";
import { Box, IconButton, Tooltip, Typography } from "@mui/material";
import { useBookInfo, useSetActiveTab } from "store/useNewBook";

export default function BookCard() {
  const [bookInfo, setBookInfo] = useBookInfo();
  const setActiveTab = useSetActiveTab();

  return (
    <Card>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h6">책</Typography>
        {bookInfo && (
          <div>
            <Tooltip
              title="서점 페이지의 도서 정보로 이동해요!"
              placement="top-start"
              arrow>
              <IconButton size="small" href={bookInfo.link} target="blank">
                <LaunchIcon fontSize="inherit" />
              </IconButton>
            </Tooltip>
            <Tooltip title="취소" placement="top-end" arrow>
              <IconButton
                size="small"
                onClick={() => {
                  setBookInfo(null);
                  setActiveTab({ type: "PREV" });
                }}>
                <ClearIcon fontSize="inherit" />
              </IconButton>
            </Tooltip>
          </div>
        )}
      </Box>
      {bookInfo && (
        <CardContent>
          <S.BookCoverImage
            src={bookInfo.cover}
            alt={bookInfo.title}
            width={80}
            height={100}
          />
          <Box sx={{ display: "flex", flexDirection: "column", width: "100%" }}>
            <Typography variant="body1">{bookInfo.title}</Typography>
            <S.BookDescription variant="body2">
              {bookInfo.author}
            </S.BookDescription>
          </Box>
        </CardContent>
      )}
    </Card>
  );
}
