import { usePostNewBookClub } from "@api/bookClub/queries";
import Navigation from "@components/common/Navigation";
import useEmail from "@hooks/useEmail";
import { Stack, Typography } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useBookClub, useBookClubMemberEmails } from "store/useBookClub";
import MemberEmailInput from "./MemberEmailInput";
import MemberEmails from "./MemberEmails";

export default function BookClubMember({
  onPrev,
  onNext,
}: {
  onPrev: () => void;
  onNext: () => void;
}) {
  const [bookClubInfo, setBookClubInfo] = useBookClub();
  const [memberEmails, setMemberEmails] = useBookClubMemberEmails();
  const { sendEmail } = useEmail();

  const { onPostNewBookClub } = usePostNewBookClub({
    onSuccessCallback: (newBookClubInfo) => {
      onNext();
      sendInvitationEmail(newBookClubInfo.invitationUrl);
      setBookClubInfo({
        type: "UPDATE",
        payload: {
          ...newBookClubInfo,
        },
      });
    },
  });

  const sendInvitationEmail = (invitationUrl: string) => {
    bookClubInfo?.name &&
      !!memberEmails.length &&
      sendEmail({
        bookClubName: bookClubInfo.name,
        invitationUrl,
        memberEmails,
      });
  };

  const addMemberEmail = (email: string) => {
    setMemberEmails({ type: "ADD_EMAIL", payload: email });
  };

  const deleteMemberEmail = (email: string) => {
    setMemberEmails({ type: "DELETE_EMAIL", payload: email });
  };

  const onPost = () => {
    if (!bookClubInfo?.name) {
      return enqueueSnackbar("북클럽 이름은 필수로 입력해주세요!", {
        variant: "error",
      });
    }

    onPostNewBookClub({
      name: bookClubInfo.name,
      profileImage: bookClubInfo.profileImage,
    });
  };

  return (
    <Stack
      gap={4}
      display="flex"
      alignItems="center"
      justifyContent="center"
      width="100%">
      <Navigation
        onPrev={{ onClick: onPrev, text: "이전 단계" }}
        onNext={{ onClick: onPost, text: "완료" }}
      />
      <Stack
        gap={4}
        alignItems="center"
        justifyContent="center"
        minHeight="60%">
        <Typography variant="h3">새로운 북클럽을 만드는 중이에요!</Typography>
        <Typography variant="body1">
          북클럽에 초대할 멤버의 이메일을 입력해주세요.
        </Typography>
        <Stack
          display="flex"
          gap={2}
          minHeight="80%"
          width="100%"
          alignItems="center"
          justifyContent="center">
          <MemberEmailInput {...{ addMemberEmail }} />
          <Typography variant="body1">{`지금까지 ${memberEmails.length}명의 북클럽 멤버를 추가했어요!`}</Typography>
          <MemberEmails {...{ memberEmails, deleteMemberEmail }} />
        </Stack>
      </Stack>
    </Stack>
  );
}
