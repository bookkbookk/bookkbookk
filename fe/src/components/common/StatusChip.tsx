import { BookChapterStatus } from "@api/chapters/type";
import { BOOK_CHAPTER_STATUS } from "@components/constants";
import { Chip } from "@mui/material";

export default function StatusChip({
  statusId,
}: {
  statusId: BookChapterStatus["id"];
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
  [BOOK_CHAPTER_STATUS.BEFORE_READING.id]: "error" as const,
  [BOOK_CHAPTER_STATUS.READING.id]: "primary" as const,
  [BOOK_CHAPTER_STATUS.AFTER_READING.id]: "info" as const,
};

const STATUS_CHIP_LABEL = {
  [BOOK_CHAPTER_STATUS.BEFORE_READING.id]:
    BOOK_CHAPTER_STATUS.BEFORE_READING.label,
  [BOOK_CHAPTER_STATUS.READING.id]: BOOK_CHAPTER_STATUS.READING.label,
  [BOOK_CHAPTER_STATUS.AFTER_READING.id]:
    BOOK_CHAPTER_STATUS.AFTER_READING.label,
};
