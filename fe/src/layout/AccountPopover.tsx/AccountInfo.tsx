import { Box, Typography } from "@mui/material";
import { useMemberValue } from "store/useMember";

export default function AccountInfo() {
  const memberInfo = useMemberValue();

  return (
    <Box sx={{ my: 1.5, px: 2 }}>
      <Typography variant="subtitle2" noWrap>
        {memberInfo?.nickname}
      </Typography>
      <Typography variant="body2" sx={{ color: "text.secondary" }} noWrap>
        {memberInfo?.email}
      </Typography>
    </Box>
  );
}
