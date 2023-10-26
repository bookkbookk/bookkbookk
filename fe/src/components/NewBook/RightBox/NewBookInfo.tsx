import { BoxContent } from "@components/common/common.style";
import BookCard from "./BookCard";
import BookChaptersCard from "./BookChaptersCard";
import BookClubGatheringCard from "./BookClubGatheringCard";

export default function NewBookInfo() {
  return (
    <BoxContent>
      <BookCard />
      <BookClubGatheringCard />
      <BookChaptersCard />
    </BoxContent>
  );
}
