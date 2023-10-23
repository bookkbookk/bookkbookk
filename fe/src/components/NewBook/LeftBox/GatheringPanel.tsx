import { useActiveTabValue } from "store/useNewBook";
import NewBookTabPanel from "./NewBookTabPanel";

export default function GatheringPanel({ index }: { index: number }) {
  const activeTabID = useActiveTabValue();

  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <div>BookClubGathering</div>
    </NewBookTabPanel>
  );
}
