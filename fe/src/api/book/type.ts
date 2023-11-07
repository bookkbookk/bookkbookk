export type BookInfo = {
  title: string;
  link: string;
  author: string;
  pubDate: string;
  description: string;
  isbn: string;
  cover: string;
  category: string;
  publisher: string;
};

export type NewBookInfo = Pick<
  BookInfo,
  "isbn" | "title" | "author" | "cover" | "category"
>;

export type NewBookBody = NewBookInfo & {
  bookClubId: number;
};

export type Chapter = {
  title: string;
  topics?: { title: string }[];
};

export type NewChapterBody = {
  bookId: number;
  chapters: Chapter[];
};

type Pagination = {
  totalItemCounts: number;
  totalPageCounts: number;
  currentPageIndex: number;
};

export type BookList = {
  pagination: Pagination;
  books: BookListItem[];
};

export type Book = {
  id: number;
  title: string;
  cover: string;
  author: string;
  category: string;
};

export type BookListItem = Book & {
  bookClub: { id: number; name: string };
};
