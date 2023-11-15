import { BookChapterStatusID } from "@api/chapters/type";
import { BOOK_CHAPTER_TABS } from "@components/constants";
import { Chip } from "@mui/material";

export default function StatusChip({
  statusId,
}: {
  statusId: BookChapterStatusID["id"];
}) {
  return (
    <Chip
      label={STATUS_CHIP_LABEL[statusId]}
      size="small"
      color={STATUS_CHIP_COLOR[statusId]}
      sx={{ fontWeight: 700, minWidth: "4rem" }}
    />
  );
}

const STATUS_CHIP_COLOR = {
  [BOOK_CHAPTER_TABS.BEFORE_READING.id]: "error" as const,
  [BOOK_CHAPTER_TABS.READING.id]: "primary" as const,
  [BOOK_CHAPTER_TABS.AFTER_READING.id]: "info" as const,
};

const STATUS_CHIP_LABEL = {
  [BOOK_CHAPTER_TABS.BEFORE_READING.id]: BOOK_CHAPTER_TABS.BEFORE_READING.label,
  [BOOK_CHAPTER_TABS.READING.id]: BOOK_CHAPTER_TABS.READING.label,
  [BOOK_CHAPTER_TABS.AFTER_READING.id]: BOOK_CHAPTER_TABS.AFTER_READING.label,
};
