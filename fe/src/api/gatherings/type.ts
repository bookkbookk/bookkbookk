export type NewBookClubGathering = {
  bookId: number;
  gatherings: {
    dateTime: string;
    place: string;
  }[];
};
