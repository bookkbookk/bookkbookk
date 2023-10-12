import styled from "styled-components";

export default function Main() {
  return (
    <div>
      Main 노토 산스<S>테스트</S>
    </div>
  );
}

const S = styled.div`
  font: ${({ theme }) => theme.font.brandBold32};
`;
