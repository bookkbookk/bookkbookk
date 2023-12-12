import { Button, TableCell } from "@mui/material";
import { formatDate } from "@utils/index";

export function GatheringTableItemCell({
  index,
  dateTime,
  place,
  onDelete,
}: {
  index: number;
  dateTime: string;
  place: string;
  onDelete?: (e: React.MouseEvent) => void;
}) {
  return (
    <>
      <TableCell align="center" component="th" scope="row">
        {index + 1}
      </TableCell>
      <TableCell align="center" sx={{ minWidth: 120, maxWidth: 120 }}>
        {formatDate(dateTime, {
          hour: "numeric" as const,
          minute: "numeric" as const,
          hour12: true,
        })}
      </TableCell>
      <TableCell sx={{ maxWidth: 150, overflowWrap: "break-word" }}>
        {place}
      </TableCell>
      <TableCell>
        {onDelete && (
          <Button variant="outlined" onClick={onDelete}>
            삭제
          </Button>
        )}
      </TableCell>
    </>
  );
}
