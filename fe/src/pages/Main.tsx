import { styled } from "@mui/material";
import Button from "@mui/material/Button";

export default function Main() {
  return (
    <div>
      Main 노토 산스<S>테스트</S>
      <Button color="secondary">Secondary</Button>
    </div>
  );
}

const S = styled("div")`
  font: ${({ theme }) => theme.typography.h1};
`;
