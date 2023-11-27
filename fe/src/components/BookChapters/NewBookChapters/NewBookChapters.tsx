import { RoundButton } from "@components/common/common.style";
import { Stack } from "@mui/material";
import { useSetChapterList } from "store/useChapterList";
import ChapterList from "./ChapterList";

export default function NewBookChapters() {
  const setChapterList = useSetChapterList();
  const onClickAddChapter = () => setChapterList({ type: "ADD_CHAPTER" });

  return (
    <Stack
      spacing={2}
      flex="column"
      alignItems="center"
      paddingY={4}
      width="100%">
      <ChapterList />
      <RoundButton onClick={onClickAddChapter}>새로운 챕터 추가</RoundButton>
    </Stack>
  );
}
