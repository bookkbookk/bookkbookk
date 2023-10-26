import { BookListItem } from "@api/book/type";
import { Box } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import BookCard from "./BookCard";

export default function BookCardGrid({ books }: { books: BookListItem[] }) {
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
