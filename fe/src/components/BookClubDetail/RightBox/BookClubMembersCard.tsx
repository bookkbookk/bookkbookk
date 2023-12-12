import { BookClubDetail } from "@api/bookClub/type";
import { Card, CardContent } from "@components/common/common.style";
import { Avatar, Stack, Typography } from "@mui/material";

export default function BookClubMembersCard({
  members,
}: Pick<BookClubDetail, "members">) {
  return (
    <Card>
      <Typography variant="h6">멤버</Typography>
      <CardContent>
        <ul>
          {members.map((member) => (
            <Stack
              component="li"
              key={member.id}
              display="flex"
              alignItems="center"
              gap={2}
              paddingY={1}
              flexDirection="row">
              <Avatar src={member.profileImgUrl} />
              <Stack>
                <Typography variant="subtitle1">{member.nickname}</Typography>
                <Typography variant="body2">{member.email}</Typography>
              </Stack>
            </Stack>
          ))}
        </ul>
      </CardContent>
    </Card>
  );
}
