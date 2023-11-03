import ProfileEditAvatar from "@components/SignUp/ProfileEditAvatar";
import Navigation from "@components/common/Navigation";
import { SectionDescription } from "@components/common/common.style";
import { useFileReader } from "@hooks/useFileReader";
import { Stack, TextField, Typography } from "@mui/material";
import { useRef } from "react";
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
  const bookClubNameRef = useRef<HTMLInputElement>(null);

  const onClickNextStep = () => {
    setBookClub({
      type: "UPDATE",
      payload: {
        name: bookClubNameRef.current?.value ?? bookClub?.name,
        previewUrl: previewUrl ?? bookClub?.previewUrl,
        profileImage: file ?? bookClub?.profileImage,
      },
    });
    onNext();
  };

  return (
    <Stack alignItems="center" justifyContent="center" height="60%">
      <Navigation
        onPrev={{ onClick: onPrev, text: "이전 단계" }}
        onNext={{ onClick: onClickNextStep, text: "다음 단계" }}
      />
      <Stack
        gap={4}
        alignItems="center"
        justifyContent="center"
        minHeight="60%">
        <Typography variant="h3">새로운 북클럽을 만드는 중이에요!</Typography>
        <SectionDescription>{`원하시는 북클럽의 대표 사진을 등록하고, 북클럽 이름을 입력해주세요`}</SectionDescription>
        <ProfileEditAvatar
          profileImgUrl={previewUrl ?? bookClub?.previewUrl ?? ""}
          onFileChange={handleFileChange}
        />
        <TextField
          id="nickname"
          label="북클럽 이름"
          placeholder="북클럽 이름을 입력해주세요"
          variant="outlined"
          defaultValue={bookClub?.name}
          inputRef={bookClubNameRef}
          sx={{ width: 300 }}
        />
      </Stack>
    </Stack>
  );
}
