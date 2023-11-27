import {
  BoxHeader,
  MainBox,
  RoundButton,
} from "@components/common/common.style";
import { Stack } from "@mui/material";
import { useSetChapterList } from "store/useChapterList";
import ChapterList from "./ChapterList";
import HeaderButtons from "./HeaderButtons";

export default function NewBookChapters({
  onChangeChapterViewer,
}: {
  onChangeChapterViewer: () => void;
}) {
  const setChapterList = useSetChapterList();
  const onClickAddChapter = () => setChapterList({ type: "ADD_CHAPTER" });

  return (
    <MainBox>
      <BoxHeader>
        <HeaderButtons {...{ onChangeChapterViewer }} />
      </BoxHeader>
      <Stack
        spacing={2}
        flex="column"
        alignItems="center"
        paddingY={4}
        width="100%">
        <ChapterList />
        <RoundButton onClick={onClickAddChapter}>새로운 챕터 추가</RoundButton>
      </Stack>
    </MainBox>
  );
}
