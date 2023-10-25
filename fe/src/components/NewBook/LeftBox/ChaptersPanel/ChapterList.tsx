import { Box, Divider, Paper } from "@mui/material";
import { useChapterListValue } from "store/newBook/useChapters";
import { ChapterListItem } from "./ChapterListItem";

export default function ChapterList() {
  const chapterList = useChapterListValue();

  return (
    <Paper elevation={1} sx={{ width: "100%" }}>
      {chapterList.map((chapter, index) => {
        return (
          <Box key={`chapter-list-item-${index}-${chapter.title}`}>
            <ChapterListItem index={index} chapter={chapter} />
            <Divider />
          </Box>
        );
      })}
    </Paper>
  );
}
