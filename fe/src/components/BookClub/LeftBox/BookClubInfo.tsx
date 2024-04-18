import { ClosedBookClubDetail, OpenBookClubDetail } from "@api/bookClub/type";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import {
  Avatar,
  Card,
  CardHeader,
  Chip,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { formatDate } from "@utils/index";

export default function BookClubInfo({
  bookClubDetail,
}: {
  bookClubDetail: OpenBookClubDetail | ClosedBookClubDetail;
}) {
  const isOpenBookClub = bookClubDetail.status === "OPEN";
  const createdTime = formatDate(bookClubDetail.createdTime);
  const closedTime = !isOpenBookClub && formatDate(bookClubDetail.closedTime);
  const subheaderText = `${createdTime} ~ ${closedTime || "진행중"}`;
  const lastBookTitle = isOpenBookClub ? "읽고 있는 책" : "마지막으로 읽은 책";

  // TODO: 컴포넌트 리팩토링
  return (
    <Card sx={{ width: "90%", paddingY: 2 }}>
      <CardHeader
        avatar={
          <Avatar
            sx={{ width: 60, height: 60 }}
            src={bookClubDetail.profileImgUrl}
          />
        }
        action={
          <>
            <Chip
              label={isOpenBookClub ? "OPEN" : "CLOSED"}
              size="small"
              color={isOpenBookClub ? "primary" : "secondary"}
              sx={{ fontWeight: 700 }}
            />
            <IconButton aria-label="settings">
              <MoreVertIcon />
            </IconButton>
          </>
        }
        title={
          <Stack display="flex" gap={1} flexDirection="row" alignItems="center">
            <Typography variant="h4">{bookClubDetail.name}</Typography>

            <Typography variant="body2" color="text.secondary">
              {subheaderText}
            </Typography>
          </Stack>
        }
        subheader={
          <>
            <Stack gap={1} display="flex" flexDirection="row">
              <Typography variant="body2" sx={{ fontWeight: 700 }}>
                {lastBookTitle}
              </Typography>
              <Typography variant="body2">
                {`${bookClubDetail.lastBook.name} (${bookClubDetail.lastBook.author})`}
              </Typography>
            </Stack>
            {isOpenBookClub && (
              <Stack gap={1} display="flex" flexDirection="row">
                <Typography variant="body2" sx={{ fontWeight: 700 }}>
                  다가오는 모임일
                </Typography>
                <Typography variant="body2">
                  {formatDate(bookClubDetail.upcomingGatheringDate)}
                </Typography>
              </Stack>
            )}
          </>
        }
      />
    </Card>
  );
}
