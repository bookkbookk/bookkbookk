export type Chapter = {
  title: string;
  topics?: { title: string }[];
};

export type NewChapterBody = {
  bookId: number;
  chapters: Chapter[];
};
