import { BoxContent } from "@components/common/common.style";
import BookClubCard from "./BookClubCard";
import BookClubMemberCard from "./BookClubMemberCard";

export default function NewBookClubInfo() {
  return (
    <BoxContent>
      <BookClubCard />
      <BookClubMemberCard />
    </BoxContent>
  );
}
