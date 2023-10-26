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
