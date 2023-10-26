import { usePostNewBookClub } from "@api/bookClub/queries";
import { Wrapper } from "@components/SignUp/SignUp.style";
import { ButtonWrapper } from "@components/common/common.style";
import { Button, Typography } from "@mui/material";
import { useBookClub } from "store/useBookClub";

export default function BookClubMember({
  onPrev,
  onNext,
}: {
  onPrev: () => void;
  onNext: () => void;
}) {
  // TODO: 언제 BookClubInfo 초기화 시켜야 하는지 고민
  const [bookClubInfo, setBookClubInfo] = useBookClub();

  const { onPostNewBookClub } = usePostNewBookClub({
    onSuccessCallback: onNext,
  });
  const onPost = () => {
    bookClubInfo &&
      onPostNewBookClub({
        name: bookClubInfo.name,
        profileImage: bookClubInfo.profileImage,
      });
  };

  return (
    <Wrapper>
      <Typography variant="h3">새로운 북클럽을 만드는 중이에요!</Typography>
      <Typography variant="body1">
        북클럽에 초대할 멤버를 초대해주세요
      </Typography>
      {/* TODO: 멤버 추가 테이블, 모달 */}
      <ButtonWrapper>
        <Button onClick={onPrev}>이전 단계</Button>
        <Button onClick={onPost}>완료</Button>
      </ButtonWrapper>
    </Wrapper>
  );
}
