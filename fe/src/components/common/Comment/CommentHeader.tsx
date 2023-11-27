import { CommentContent } from "@api/comments/type";
import DeleteIcon from "@mui/icons-material/Delete";
import ModeRoundedIcon from "@mui/icons-material/ModeRounded";
import { Avatar, Button, Stack, Typography } from "@mui/material";
import { convertPastTimestamp } from "@utils/index";
import { useMemberValue } from "store/useMember";
import * as S from "./style";

export default function CommentHeader(
  props: Pick<CommentContent, "author" | "createdTime">
) {
  const member = useMemberValue();
  const {
    author: { memberId, profileImgUrl, nickname },
    createdTime,
  } = props;

  const isAuthor = member?.id === memberId;

  return (
    <S.CommentHeader>
      <Stack
        display="flex"
        alignItems="center"
        gap={1}
        flexDirection="row"
        width="100%">
        <Avatar src={profileImgUrl} sx={{ width: 36, height: 36 }} />
        <Typography variant="body1">{nickname}</Typography>
        <Typography variant="body2">
          {convertPastTimestamp(createdTime)}
        </Typography>
      </Stack>
      {isAuthor && (
        <Stack display="flex" flexDirection="row">
          <Button
            size="small"
            color="inherit"
            startIcon={<ModeRoundedIcon fontSize="small" />}>
            편집
          </Button>
          <Button
            size="small"
            color="inherit"
            startIcon={<DeleteIcon fontSize="small" />}>
            삭제
          </Button>
        </Stack>
      )}
    </S.CommentHeader>
  );
}
