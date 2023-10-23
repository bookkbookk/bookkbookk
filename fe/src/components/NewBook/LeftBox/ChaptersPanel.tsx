import NewBookTabPanel from "./NewBookTabPanel";
import { NewBookPanel } from "./type";

export default function ChaptersPanel({ index, activeTabID }: NewBookPanel) {
  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <div>BookChapters</div>
    </NewBookTabPanel>
  );
}
