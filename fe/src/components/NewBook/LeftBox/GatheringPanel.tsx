import NewBookTabPanel from "./NewBookTabPanel";
import { NewBookPanel } from "./type";

export default function GatheringPanel({ index, activeTabID }: NewBookPanel) {
  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <div>BookClubGathering</div>
    </NewBookTabPanel>
  );
}
