import { useGetBooks } from "@api/book/queries";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { MESSAGE } from "@constant/index";
import { Pagination, Stack } from "@mui/material";
import React, { useState } from "react";
import { BookCardGrid, GridSkeleton } from "./BookCardGrid";

export default function BookList() {
  const [page, setPage] = useState(1);
  const handleChange = (_: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const { books, isLoading, isError, isSuccess } = useGetBooks({
    page: page - 1,
    size: 9,
  });

  return (
    <>
      {isError && (
        <StatusIndicator status="error" message={MESSAGE.BOOK_LIST_ERROR} />
      )}
      {isSuccess && books && (
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
      {isLoading && <GridSkeleton />}
    </>
  );
}
