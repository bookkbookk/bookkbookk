import { useActiveTabValue } from "store/useNewBook";
import NewBookTabPanel from "./NewBookTabPanel";

export default function ChaptersPanel({ index }: { index: number }) {
  const activeTabID = useActiveTabValue();

  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <div>BookChapters</div>
    </NewBookTabPanel>
  );
}
