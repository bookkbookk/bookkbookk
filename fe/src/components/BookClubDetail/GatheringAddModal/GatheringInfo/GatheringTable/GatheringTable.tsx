import {
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

export function GatheringTable({ tableBody }: { tableBody: React.ReactNode }) {
  return (
    <TableContainer
      sx={{
        width: "100%",
        maxHeight: "30rem",
        overflow: "scroll",
      }}>
      <Table aria-label="gathering table">
        <TableHead>
          <TableRow>
            <TableCell align="center" sx={{ minWidth: 60 }}>
              차시
            </TableCell>
            <TableCell align="center">일시</TableCell>
            <TableCell>장소</TableCell>
            <TableCell align="center" />
          </TableRow>
        </TableHead>
        {tableBody}
      </Table>
    </TableContainer>
  );
}
