import { TableBody } from "@mui/material";
import { Gathering, useGatheringValue } from "store/useGathering";
import GatheringTableItem from "./GatheringTableItem";

export default function GatheringEditTableBody({
  onClickGatheringItem,
}: {
  onClickGatheringItem: (gathering: Gathering) => void;
}) {
  const gatheringInfo = useGatheringValue();

  return (
    <TableBody>
      {gatheringInfo.gatherings.map((gathering, index) => (
        <GatheringTableItem
          key={gathering.id}
          onClick={onClickGatheringItem}
          {...{ index, gathering }}
        />
      ))}
    </TableBody>
  );
}
