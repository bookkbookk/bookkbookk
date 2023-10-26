import ProfileEditAvatar from "@components/SignUp/ProfileEditAvatar";
import { Wrapper } from "@components/SignUp/SignUp.style";
import {
  ButtonWrapper,
  SectionDescription,
} from "@components/common/common.style";
import { useFileReader } from "@hooks/useFileReader";
import { Button, TextField, Tooltip, Typography } from "@mui/material";
import { useState } from "react";
import { useBookClub } from "store/useBookClub";

export default function BookClubProfile({
  onPrev,
  onNext,
}: {
  onPrev: () => void;
  onNext: () => void;
}) {
  const [bookClub, setBookClub] = useBookClub();
  const { file, previewUrl, handleFileChange } = useFileReader({
    storedFile: bookClub?.profileImage,
  });

  const [bookClubName, setBookClubName] = useState(bookClub?.name ?? "");
  const isFilled = !!bookClubName;

  const onClickNextStep = () => {
    setBookClub({
      ...bookClub,
      name: bookClubName,
      previewUrl: previewUrl ?? bookClub?.previewUrl,
      profileImage: file ?? bookClub?.profileImage,
    });
    onNext();
  };

  return (
    <Wrapper>
      <Typography variant="h3">새로운 북클럽을 만드는 중이에요!</Typography>
      <SectionDescription>{`원하시는 북클럽의 대표 사진을 등록하고, \n 북클럽 이름을 입력해주세요`}</SectionDescription>
      <ProfileEditAvatar
        profileImgUrl={previewUrl ?? bookClub?.previewUrl ?? ""}
        onFileChange={handleFileChange}
      />
      <TextField
        id="nickname"
        label="북클럽 이름"
        placeholder="북클럽 이름을 입력해주세요"
        variant="outlined"
        value={bookClubName}
        onChange={(e) => setBookClubName(e.target.value)}
        sx={{ width: 300 }}
      />
      <ButtonWrapper>
        <Button onClick={onPrev}>이전 단계</Button>
        <Tooltip title="북클럽 이름은 필수로 입력해주세요!">
          <div>
            <Button onClick={onClickNextStep} disabled={!isFilled}>
              다음 단계
            </Button>
          </div>
        </Tooltip>
      </ButtonWrapper>
    </Wrapper>
  );
}
