import { BookListItem } from "@api/book/type";
import { PAGE_TITLE } from "@constant/index";
import { Chip, Stack, Typography } from "@mui/material";
import { Location, useLocation } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function HeaderTitle() {
  const { pathname, state }: Location<{ book: BookListItem }> = useLocation();

  const isBookChapterPath = pathname.includes(ROUTE_PATH.chapters);
  const isBookClubPath = pathname.includes(ROUTE_PATH.bookClub);

  const getHeaderTitle = () => {
    if (isBookChapterPath) {
      return `${state.book.title} - ${state.book.author}`;
    }

    if (isBookClubPath) {
      return "북클럽 조회";
    }

    return PAGE_TITLE[pathname].korean ?? "";
  };

  return (
    <Stack
      direction="row"
      alignItems="center"
      paddingX={1}
      flexGrow={1}
      spacing={1}>
      <Typography
        variant="h4"
        noWrap
        component="div"
        sx={{ fontFamily: "SOYO Maple Bold", maxWidth: "60vw" }}>
        {getHeaderTitle()}
      </Typography>
      {isBookChapterPath && (
        <Chip
          label={`${state.book.bookClub.name} 북클럽`}
          size="small"
          color="primary"
          sx={{ fontWeight: 700 }}
        />
      )}
    </Stack>
  );
}
