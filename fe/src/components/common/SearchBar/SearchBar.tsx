import SearchIcon from "@mui/icons-material/Search";
import * as S from "./SearchBar.style";

export default function SearchBar({
  placeholder,
  value,
  onChange,
}: {
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}) {
  return (
    <S.Search>
      <S.SearchIconWrapper>
        <SearchIcon />
      </S.SearchIconWrapper>
      <S.InputBase
        placeholder={placeholder}
        inputProps={{ "aria-label": "search", value, onChange }}
      />
    </S.Search>
  );
}
