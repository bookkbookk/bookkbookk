import { BookInfo } from "@api/book/type";
import { List, ListItem, Paper } from "@mui/material";
import { useState } from "react";
import BookDetailModal from "../Modals/BookDetailModal";
import { SearchBookListItem } from "./SearchBookListItem";

export default function SearchBookList({
  data,
  onSelectBook,
}: {
  data: BookInfo[];
  onSelectBook: () => void;
}) {
  const [selectedBook, setSelectedBook] = useState<BookInfo | null>(null);
  const handleModalOpen = (bookInfo: BookInfo) => setSelectedBook(bookInfo);
  const handleModalClose = () => setSelectedBook(null);

  return (
    <Paper>
      <List sx={{ padding: 0 }}>
        {data.map((bookInfo) => (
          <ListItem
            key={bookInfo.isbn}
            disablePadding
            onClick={() => handleModalOpen(bookInfo)}>
            <SearchBookListItem {...bookInfo} />
          </ListItem>
        ))}
      </List>
      {selectedBook && (
        <BookDetailModal
          open={!!selectedBook}
          handleClose={handleModalClose}
          bookInfo={selectedBook}
          onSelectBook={onSelectBook}
        />
      )}
    </Paper>
  );
}
