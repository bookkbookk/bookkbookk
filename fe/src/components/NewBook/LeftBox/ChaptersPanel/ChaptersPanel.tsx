import { RoundButton } from "@components/common/common.style";
import { Box } from "@mui/material";
import { useSetChapterList } from "store/newBook/useChapters";
import { useActiveTabValue } from "store/useNewBook";
import NewBookTabPanel from "../NewBookTabPanel";
import ChapterList from "./ChapterList";

export default function ChaptersPanel({ index }: { index: number }) {
  const activeTabID = useActiveTabValue();
  const setChapterList = useSetChapterList();
  const onClickAddChapter = () => setChapterList({ type: "ADD_CHAPTER" });

  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: "2rem",
          padding: "1rem 0",
          width: "95%",
        }}>
        <ChapterList />
        <RoundButton onClick={onClickAddChapter}>새로운 챕터 추가</RoundButton>
      </Box>
    </NewBookTabPanel>
  );
}
