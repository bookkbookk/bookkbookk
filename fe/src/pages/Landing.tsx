import Header from "@components/Landing/Header";
import Main from "@components/Landing/Main";
import { Container } from "@components/Landing/index.style";
import { Navigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function Landing() {
  const isLoginValue = useIsLoginValue();

  if (isLoginValue === true) {
    return <Navigate to={ROUTE_PATH.main} replace />;
  }

  return (
    <Container>
      <Header />
      <Main />
    </Container>
  );
}
