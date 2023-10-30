import { usePostNewChapters } from "@api/chapters/queries";
import { MESSAGE } from "@constant/index";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Button } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useParams } from "react-router-dom";
import { useChapterList } from "store/useChapterList";
import { useTopicList } from "store/useTopicList";

export default function HeaderButtons({
  onChangeChapterViewer,
}: {
  onChangeChapterViewer: () => void;
}) {
  const { bookId } = useParams<{ bookId: string }>();
  const [chapterList, setChapterList] = useChapterList();
  const [topicList, setTopicList] = useTopicList();

  const { onPostChapters } = usePostNewChapters({
    onSuccessCallback: () => {
      onChangeChapterViewer();
      setChapterList({ type: "RESET_CHAPTER" });
      setTopicList({ type: "RESET_TOPIC" });
    },
  });

  const onClickPostChapters = () => {
    const isValidChapters = validateChapters();

    if (!isValidChapters) {
      return enqueueSnackbar(MESSAGE.INVALID_NEW_CHAPTERS, {
        variant: "error",
      });
    }

    onPostChapters({
      bookId: Number(bookId),
      chapters: chapterList.map((chapter, chapterIndex) => ({
        ...chapter,
        topics: topicList.get(chapterIndex),
      })),
    });
  };

  const validateChapters = () => {
    const isValidChapters = chapterList.every(
      (chapter) => chapter.title !== ""
    );
    const isValidTopics = [...topicList.values()].every((topics) =>
      topics.every((topic) => topic.title !== "")
    );

    return isValidChapters && isValidTopics;
  };

  return (
    <>
      <Button onClick={onChangeChapterViewer}>
        <ArrowBackIcon />
        챕터 목록으로 돌아가기
      </Button>
      <Button
        variant="contained"
        onClick={onClickPostChapters}
        disabled={chapterList.length === 0}>
        챕터 추가 완료하기
      </Button>
    </>
  );
}
