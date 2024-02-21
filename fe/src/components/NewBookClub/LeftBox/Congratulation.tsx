import CongratulationImage from "@assets/images/congratulation.png";
import { SectionDescription } from "@components/common/common.style";
import { MESSAGE } from "@constant/index";
import { Box, Button, Stack, Typography } from "@mui/material";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useBookClub, useSetBookClubMemberEmails } from "store/useBookClub";

export function BookClubCongratulation() {
  const navigate = useNavigate();
  const [bookClubInfo, setBookClubInfo] = useBookClub();
  const setMemberEmails = useSetBookClubMemberEmails();

  useEffect(() => {
    return () => {
      setBookClubInfo({ type: "RESET" });
      setMemberEmails({ type: "RESET" });
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Stack
      gap={4}
      display="flex"
      alignItems="center"
      justifyContent="center"
      width="100%">
      <Typography variant="h3" sx={{ textAlign: "center" }}>
        북클럽 생성을 완료했어요!
      </Typography>
      <img src={CongratulationImage} alt="congratulation" />
      <SectionDescription>{MESSAGE.NEW_BOOK_CLUB_SUCCESS}</SectionDescription>
      <Box sx={{ display: "flex", gap: "2rem" }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate(ROUTE_PATH.newBook)}>
          책 추가하러 가기
        </Button>
        <Button
          variant="contained"
          color="primary"
          onClick={() =>
            navigate(`${ROUTE_PATH.bookClub}/${bookClubInfo?.bookClubId}`)
          }>
          방금 만든 북클럽 확인하기
        </Button>
      </Box>
    </Stack>
  );
}
