import NewBookTabs from "@components/NewBook/LeftBox/NewBookTabs";
import * as S from "@components/common/common.style";
import BackupIcon from "@mui/icons-material/Backup";
import { Button } from "@mui/material";
import ChaptersPanel from "./ChaptersPanel";
import GatheringPanel from "./GatheringPanel/GatheringPanel";
import SearchPanel from "./SearchPanel";

export default function NewBookLeftBox() {
  // TODO: 책 추가 완료하기 버튼 클릭 시, 책 추가 API 호출
  return (
    <S.LeftBox
      sx={{
        alignItems: "flex-start",
        justifyContent: "flex-start",
        padding: "1rem 2rem",
      }}>
      <S.BoxHeader>
        <NewBookTabs />
        <Button variant="contained" startIcon={<BackupIcon />}>
          책 추가 완료하기
        </Button>
      </S.BoxHeader>
      <SearchPanel index={0} />
      <GatheringPanel index={1} />
      <ChaptersPanel index={2} />
    </S.LeftBox>
  );
}
