import { BookChapterStatus } from "@api/chapters/type";
import { BOOK_CHAPTER_STATUS } from "@components/constants";
import { Chip, Tooltip } from "@mui/material";

export default function StatusChip({
  statusId,
  onClick,
}: {
  statusId: BookChapterStatus["id"];
  onClick?: React.MouseEventHandler<HTMLDivElement>;
}) {
  return (
    <>
      {onClick ? (
        <Tooltip title="독서 상태를 변경하세요" placement="top" arrow>
          <Chip
            onClick={onClick}
            label={STATUS_CHIP_LABEL[statusId]}
            size="small"
            color={STATUS_CHIP_COLOR[statusId]}
            sx={{ fontWeight: 700, minWidth: "4rem" }}
          />
        </Tooltip>
      ) : (
        <Chip
          label={STATUS_CHIP_LABEL[statusId]}
          size="small"
          color={STATUS_CHIP_COLOR[statusId]}
          sx={{ fontWeight: 700, minWidth: "4rem" }}
        />
      )}
    </>
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
