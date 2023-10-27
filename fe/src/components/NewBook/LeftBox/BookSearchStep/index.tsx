import { useGetBookSearchResult } from "@api/book/queries";
import SearchBar from "@components/common/SearchBar/SearchBar";
import { useDebounceValue } from "@hooks/useDebounceValue";

import { Box } from "@mui/material";
import { useState } from "react";
import { BookSearchGuide } from "./BookSearchGuide";

import Navigation from "../Navigation";
import SearchBookList from "./SearchBookList";
export default function BookSearchStep({
  onPrev,
  onNext,
}: {
  onPrev: () => void;
  onNext: () => void;
}) {
  const [userInput, setUserInput] = useState("");
  const searchWord = useDebounceValue({
    value: userInput,
    wait: 500,
  });
  const { bookSearchResult } = useGetBookSearchResult(searchWord);

  const onUserInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInput(e.target.value);
  };

  const resetUserInput = () => {
    setUserInput("");
    onNext();
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        width: "100%",
        gap: "1.5rem",
      }}>
      <Navigation {...{ onPrev, onNext }} />
      <SearchBar
        placeholder="책 이름을 검색하세요"
        value={userInput}
        onChange={onUserInputChange}
      />
      {!userInput && !bookSearchResult?.length && <BookSearchGuide />}
      {!!bookSearchResult?.length && (
        <SearchBookList data={bookSearchResult} onSelectBook={resetUserInput} />
      )}
    </Box>
  );
}
