import emotionStyled from "@emotion/styled";
import Button from "@mui/material/Button";

export default function Main() {
  return (
    <div>
      Main 노토 산스<S>테스트</S>
      <Button color="secondary">Secondary</Button>
    </div>
  );
}

const S = emotionStyled.div`
  font: ${({ theme }) => theme.font.brandBold32};
`;
