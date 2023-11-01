import { Card } from "@components/common/common.style";
import { Stack, Typography } from "@mui/material";
import { useBookClubMemberEmailsValue } from "store/useBookClub";

export default function BookClubMemberCard() {
  const memberEmails = useBookClubMemberEmailsValue();

  return (
    <Card>
      <Typography variant="h6">초대한 멤버 이메일 목록</Typography>
      {!!memberEmails.length && (
        <Stack component="ul" gap={1}>
          {memberEmails.map((email, index) => (
            <li key={email}>
              <Typography variant="body1" key={email}>
                {`${index + 1}. ${email}`}
              </Typography>
            </li>
          ))}
        </Stack>
      )}
    </Card>
  );
}
