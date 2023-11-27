import { ChapterListItem } from "@api/chapters/type";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import { TreeView } from "@mui/x-tree-view";
import { ChapterItem } from "./ChapterItem";

export function ChapterTree({
  chapterList,
}: {
  chapterList: ChapterListItem[];
}) {
  return (
    <TreeView
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        flexDirection: "column",
      }}
      aria-label="chapter tree"
      defaultCollapseIcon={<ArrowDropDownIcon />}
      defaultExpandIcon={<ArrowRightIcon />}>
      {chapterList.map((chapterListItem) => (
        <ChapterItem
          key={chapterListItem.chapterId}
          chapter={chapterListItem}
        />
      ))}
    </TreeView>
  );
}
