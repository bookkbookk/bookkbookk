import { MainBox } from "@components/common/common.style";
import { useParams } from "react-router-dom";

export default function BookClub() {
  const { bookClubId } = useParams<{ bookClubId: string }>();

  return <MainBox>{bookClubId}</MainBox>;
}
