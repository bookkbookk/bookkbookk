import NewBookTabs from "@components/NewBook/LeftBox/NewBookTabs";
import * as S from "@components/common/common.style";
import ChaptersPanel from "./ChaptersPanel";
import GatheringPanel from "./GatheringPanel";
import SearchPanel from "./SearchPanel";

export default function NewBookLeftBox() {
  return (
    <S.LeftBox
      sx={{
        alignItems: "flex-start",
        justifyContent: "flex-start",
        padding: "1rem 2rem",
      }}>
      <NewBookTabs />
      <SearchPanel index={0} />
      <GatheringPanel index={1} />
      <ChaptersPanel index={2} />
    </S.LeftBox>
  );
}
