import { queryKeys } from "@api/queryKeys";
import { useQuery } from "@tanstack/react-query";

export const useGetBookSearchResult = (searchWord: string) =>
  useQuery(queryKeys.book.search(searchWord));
