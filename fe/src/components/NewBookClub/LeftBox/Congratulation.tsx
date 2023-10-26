import CongratulationImage from "@assets/images/congratulation.png";
import { SectionDescription } from "@components/common/common.style";
import { MESSAGE } from "@constant/index";
import { Box, Button, Typography } from "@mui/material";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useSetBookClub } from "store/useBookClub";

export function BookClubCongratulation() {
  const navigate = useNavigate();
  const setBookClubInfo = useSetBookClub();

  useEffect(() => {
    return () => setBookClubInfo(null);

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
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
            console.log("TODO: 해당 북클럽 상세 조회 페이지로 이동")
          }>
          방금 만든 북클럽 확인하기
        </Button>
      </Box>
    </>
  );
}
