import { Gathering, useSetGathering } from "store/useGathering";
import * as S from "../style";
import { GatheringTableItemCell } from "./GatheringTableItemCell";

export default function GatheringTableItem({
  index,
  gathering,
  onClick,
}: {
  index: number;
  gathering: Gathering;
  onClick: (gathering: Gathering) => void;
}) {
  const setGathering = useSetGathering();
  const onDelete = (e: React.MouseEvent) => {
    e.stopPropagation();
    setGathering({
      type: "DELETE_GATHERING",
      payload: { gatheringId: gathering.id },
    });
  };

  return (
    <S.GatheringTableItem onClick={() => onClick(gathering)}>
      <GatheringTableItemCell
        index={index}
        dateTime={gathering.dateTime}
        place={gathering.place}
        onDelete={onDelete}
      />
    </S.GatheringTableItem>
  );
}
