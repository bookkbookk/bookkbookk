import { BookListItem } from "@api/book/type";
import { Box, Paper, Skeleton, Stack } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import BookCard from "./BookCard";

export function BookCardGrid({ books }: { books: BookListItem[] }) {
  return (
    <Box sx={{ flexGrow: 1, padding: "2rem" }}>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}>
        {books.map((book, index) => (
          <Grid xs={2} sm={4} md={4} key={index}>
            <BookCard book={book} />
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}

export function GridSkeleton() {
  return (
    <Box sx={{ flexGrow: 1, padding: "2rem" }}>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}>
        {Array.from({ length: 6 }).map((_, index) => (
          <Grid xs={2} sm={4} md={4} key={index}>
            <Paper sx={{ padding: "2rem" }}>
              <Stack spacing={1}>
                <Skeleton variant="text" sx={{ fontSize: "2rem" }} />
                <Skeleton variant="rounded" height={200} animation="wave" />
                <Stack>
                  <Skeleton variant="text" sx={{ fontSize: "2rem" }} />
                  <Skeleton variant="text" sx={{ fontSize: "1.5rem" }} />
                </Stack>
              </Stack>
            </Paper>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
