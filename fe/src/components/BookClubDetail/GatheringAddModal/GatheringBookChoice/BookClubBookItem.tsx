import { Book } from "@api/book/type";
import BookInfo from "@components/common/BookInfo";
import { ListItemButton } from "@components/common/common.style";
import { useSetGathering } from "store/useGathering";

export function BookClubBookItem({
  book,
  onClick,
}: {
  book: Book;
  onClick: () => void;
}) {
  const setGathering = useSetGathering();

  const onItemClick = () => {
    setGathering({ type: "SELECT_BOOK", payload: { book } });
    onClick();
  };

  return (
    <ListItemButton onClick={onItemClick}>
      <BookInfo book={book} />
    </ListItemButton>
  );
}
