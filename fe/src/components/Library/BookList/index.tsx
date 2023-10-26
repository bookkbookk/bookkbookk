import { useGetBooks } from "@api/book/queries";
import { Pagination, Stack } from "@mui/material";
import React, { useState } from "react";
import BookCardGrid from "./BookCardGrid";

export default function BookList() {
  const [page, setPage] = useState(1);
  const handleChange = (_: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const { books } = useGetBooks({ page: page - 1, size: 6 });

  return (
    <>
      {books && (
        <Stack spacing={2}>
          <BookCardGrid books={books.books} />
          <Pagination
            sx={{ display: "flex", justifyContent: "center" }}
            count={books.pagination.totalPageCounts}
            page={page}
            onChange={handleChange}
          />
        </Stack>
      )}
    </>
  );
}
