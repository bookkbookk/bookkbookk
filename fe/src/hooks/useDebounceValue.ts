import { debounce } from "@mui/material";
import { useEffect, useState } from "react";

export const useDebounceValue = <T>({
  value,
  wait,
}: {
  value: T;
  wait: number;
}): T => {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    const debounceChangeSearchWord = debounce(setDebouncedValue, wait);
    debounceChangeSearchWord(value);

    return () => {
      debounceChangeSearchWord.clear();
    };
  }, [value, wait]);

  return debouncedValue;
};
