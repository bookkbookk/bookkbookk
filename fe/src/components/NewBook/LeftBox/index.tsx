import NewBookTabs from "@components/NewBook/LeftBox/NewBookTabs";
import * as S from "@components/common/common.style";
import { NEW_BOOK_TABS } from "@components/constants";
import React, { useState } from "react";
import ChaptersPanel from "./ChaptersPanel";
import GatheringPanel from "./GatheringPanel";
import SearchPanel from "./SearchPanel";

export default function NewBookLeftBox() {
  const [activeTabID, setActiveTabID] = useState(NEW_BOOK_TABS[0].id);
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setActiveTabID(newValue);
  };

  return (
    <S.LeftBox
      sx={{
        alignItems: "flex-start",
        justifyContent: "flex-start",
        padding: "1rem 2rem",
      }}>
      <NewBookTabs {...{ activeTabID, handleChange }} />
      <SearchPanel index={0} activeTabID={activeTabID} />
      <GatheringPanel index={1} activeTabID={activeTabID} />
      <ChaptersPanel index={2} activeTabID={activeTabID} />
    </S.LeftBox>
  );
}
