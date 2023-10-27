import { useGetBookSearchResult } from "@api/book/queries";
import SearchBar from "@components/common/SearchBar/SearchBar";
import { useDebounceValue } from "@hooks/useDebounceValue";
import { Box } from "@mui/material";
import { useState } from "react";
import { useActiveTabValue } from "store/useNewBook";
import NewBookTabPanel from "../NewBookTabPanel";
import { BookSearchGuide } from "./BookSearchGuide";
import SearchBookList from "./SearchBookList";

export default function BookSearch({ index }: { index: number }) {
  const activeTabID = useActiveTabValue();
  const [userInput, setUserInput] = useState("");
  const searchWord = useDebounceValue({
    value: userInput,
    wait: 500,
  });
  const { data: bookSearchResult } = useGetBookSearchResult(searchWord);

  const onUserInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInput(e.target.value);
  };

  const resetUserInput = () => setUserInput("");

  return (
    <NewBookTabPanel index={index} value={activeTabID}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          gap: "1rem",
        }}>
        <SearchBar
          placeholder="책 이름을 검색하세요"
          value={userInput}
          onChange={onUserInputChange}
        />
        {!userInput && <BookSearchGuide />}
        {!!bookSearchResult?.length && (
          <SearchBookList
            data={bookSearchResult}
            onSelectBook={resetUserInput}
          />
        )}
      </Box>
    </NewBookTabPanel>
  );
}